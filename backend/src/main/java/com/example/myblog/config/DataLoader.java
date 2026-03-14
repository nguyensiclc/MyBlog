package com.example.myblog.config;

import com.example.myblog.about.About;
import com.example.myblog.about.AboutRepository;
import com.example.myblog.role.Role;
import com.example.myblog.quote.Quote;
import com.example.myblog.quote.QuoteRepository;
import com.example.myblog.role.RoleRepository;
import com.example.myblog.user.User;
import com.example.myblog.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Seed data: common roles table + sample admin account (tdn / tdn123).
 */
@Configuration
public class DataLoader {

    @Bean
    public CommandLineRunner seedRolesAndAdmin(
            RoleRepository roleRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            AboutRepository aboutRepository,
            QuoteRepository quoteRepository
    ) {
        return args -> {
            if (quoteRepository.count() == 0) {
                seedQuotes(quoteRepository);
            }
            if (aboutRepository.findById(1L).isEmpty()) {
                aboutRepository.save(About.builder().id(1L).content("").build());
            }
            if (!roleRepository.existsByCode("ROLE_USER")) {
                roleRepository.save(Role.builder().code("ROLE_USER").name("Người dùng").build());
            }
            if (!roleRepository.existsByCode("ROLE_ADMIN")) {
                roleRepository.save(Role.builder().code("ROLE_ADMIN").name("Quản trị viên").build());
            }

            var adminUser = userRepository.findByUsername("tdn");
            if (adminUser.isEmpty()) {
                User admin = User.builder()
                        .username("tdn")
                        .passwordHash(passwordEncoder.encode("tdn123"))
                        .roles("ROLE_USER,ROLE_ADMIN")
                        .build();
                userRepository.save(admin);
            } else {
                // Reset admin password so tdn/tdn123 always works (e.g. after DB change or first failed seed)
                User u = adminUser.get();
                u.setPasswordHash(passwordEncoder.encode("tdn123"));
                u.setRoles("ROLE_USER,ROLE_ADMIN");
                userRepository.save(u);
            }
        };
    }

    private static void seedQuotes(QuoteRepository repo) {
        String[][] quotes = {
            { "Đi một ngày đàng, học một sàng khôn.", "Tục ngữ" },
            { "Có công mài sắt, có ngày nên kim.", "Tục ngữ" },
            { "Thất bại là mẹ thành công.", "Tục ngữ" },
            { "Học thầy không tày học bạn.", "Tục ngữ" },
            { "Ăn quả nhớ kẻ trồng cây.", "Tục ngữ" },
            { "Uống nước nhớ nguồn.", "Tục ngữ" },
            { "Lời nói chẳng mất tiền mua, lựa lời mà nói cho vừa lòng nhau.", "Tục ngữ" },
            { "Gần mực thì đen, gần đèn thì sáng.", "Tục ngữ" },
            { "Tốt gỗ hơn tốt nước sơn.", "Tục ngữ" },
            { "Kiến tha lâu cũng đầy tổ.", "Tục ngữ" },
            { "Có chí thì nên.", "Tục ngữ" },
            { "Thua keo này bày keo khác.", "Tục ngữ" },
            { "Một cây làm chẳng nên non, ba cây chụm lại nên hòn núi cao.", "Tục ngữ" },
            { "Đoàn kết là sức mạnh.", "Tục ngữ" },
            { "Học ăn, học nói, học gói, học mở.", "Tục ngữ" },
            { "Làm khi lành để dành khi đau.", "Tục ngữ" },
            { "Đầu xuôi đuôi lọt.", "Tục ngữ" },
            { "Có bột mới gột nên hồ.", "Tục ngữ" },
            { "Trăm hay không bằng tay quen.", "Tục ngữ" },
            { "Học một biết mười.", "Tục ngữ" },
            { "Sống là cho đâu chỉ nhận riêng mình.", "Tố Hữu" },
            { "Cuộc đời là những chuyến đi, đừng dừng lại khi vẫn còn có thể.", null },
            { "Thành công là đứng dậy nhiều hơn số lần ngã.", null },
            { "Hôm nay khó khăn, ngày mai sẽ dễ dàng hơn nếu bạn không bỏ cuộc.", null },
            { "Sự khác biệt giữa ước mơ và thành tựu là hành động.", null },
            { "Học từ ngày hôm qua, sống cho hôm nay, hy vọng cho ngày mai.", "Albert Einstein" },
            { "Cách bắt đầu tốt nhất là ngừng nói và bắt tay vào làm.", "Walt Disney" },
            { "Code là thơ được viết bằng logic.", null },
            { "Đơn giản là đỉnh cao của sự tinh tế.", "Leonardo da Vinci" },
            { "Mỗi ngày là một trang mới, hãy viết điều đáng nhớ.", null },
        };
        for (String[] q : quotes) {
            repo.save(Quote.builder().content(q[0]).author(q[1]).build());
        }
    }
}
