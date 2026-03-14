/**
 * Extract text from PDF file (browser) and parse into CV sections.
 * Uses pdfjs-dist. Section headers detected in Vietnamese and English.
 */

const SECTION_PATTERNS = [
  { key: 'experience', regex: /^(?:kinh\s* nghi[eệ]m|experience|employment|work\s*experience|ngh[eề] nghi[eệ]p|l[iị]ch\s*s[eử])\s*:?\s*$/i },
  { key: 'education', regex: /^(?:h[eọ]c\s*v[eấ]n|education|academic|formation|b[eằ]ng\s*c[eấ]p|tr[uưở]ng)\s*:?\s*$/i },
  { key: 'skills', regex: /^(?:k[ỹy]\s*n[aă]ng|skills|technical\s*skills|comp[eé]tences|k[ỹy]\s*thu[eậ]t)\s*:?\s*$/i },
  { key: 'contact', regex: /^(?:li[eê]n\s*h[eệ]|contact|th[oô]ng\s*tin\s*li[eê]n\s*h[eệ])\s*:?\s*$/i },
  { key: 'intro', regex: /^(?:gi[eớ]i\s*thi[eệ]u|about|summary|objective|m[uụ]c\s*ti[eê]u|profile|m[oô]\s*t[eả])\s*:?\s*$/i }
];

function findSectionKey(line) {
  const trimmed = line.trim();
  for (const { key, regex } of SECTION_PATTERNS) {
    if (regex.test(trimmed)) return key;
  }
  return null;
}

/**
 * Parse raw CV text into sections.
 * @param {string} fullText - Raw text from PDF
 * @returns {{ name: string, title: string, intro: string, experience: string, education: string, skills: string, contact: string }}
 */
export function parseCvText(fullText) {
  const result = {
    name: '',
    title: '',
    intro: '',
    experience: '',
    education: '',
    skills: '',
    contact: ''
  };

  if (!fullText || typeof fullText !== 'string') return result;

  const lines = fullText.split(/\r?\n/).map((l) => l.trimEnd());
  let currentSection = 'intro';
  const buffers = { intro: [], experience: [], education: [], skills: [], contact: [] };
  let firstLine = true;
  let nameLine = '';
  let titleLine = '';

  for (let i = 0; i < lines.length; i++) {
    const line = lines[i];
    const sectionKey = findSectionKey(line);
    if (sectionKey && buffers[sectionKey] !== undefined) {
      currentSection = sectionKey;
      continue;
    }
    if (line === '') continue;

    if (currentSection === 'intro' && firstLine) {
      if (!nameLine) {
        nameLine = line;
      } else if (!titleLine && nameLine) {
        titleLine = line;
      } else {
        buffers.intro.push(line);
      }
      firstLine = false;
      continue;
    }

    if (buffers[currentSection]) {
      buffers[currentSection].push(line);
    }
  }

  result.name = nameLine || '';
  result.title = titleLine || '';
  result.intro = buffers.intro.join('\n').trim();
  result.experience = buffers.experience.join('\n').trim();
  result.education = buffers.education.join('\n').trim();
  result.skills = buffers.skills.join('\n').trim();
  result.contact = buffers.contact.join('\n').trim();

  return result;
}

/**
 * Extract text from a PDF File using pdfjs-dist.
 * @param {File} file - PDF file
 * @returns {Promise<string>} Full text of the PDF
 */
export async function extractTextFromPdf(file) {
  const pdfjsLib = await import('pdfjs-dist/build/pdf.mjs');

  const arrayBuffer = await file.arrayBuffer();
  const pdf = await pdfjsLib.getDocument(arrayBuffer).promise;
  const numPages = pdf.numPages;
  let fullText = '';

  for (let pageNum = 1; pageNum <= numPages; pageNum++) {
    const page = await pdf.getPage(pageNum);
    const textContent = await page.getTextContent();
    const pageText = textContent.items.map((item) => item.str).join(' ');
    fullText += pageText + '\n';
  }

  return fullText.trim();
}

/**
 * Load PDF file, extract text, parse into CV sections.
 * @param {File} file - PDF file (must be application/pdf)
 * @returns {Promise<{ name, title, intro, experience, education, skills, contact }>}
 */
export async function parsePdfCv(file) {
  if (!file || file.type !== 'application/pdf') {
    throw new Error('Vui lòng chọn file PDF.');
  }
  const text = await extractTextFromPdf(file);
  return parseCvText(text);
}
