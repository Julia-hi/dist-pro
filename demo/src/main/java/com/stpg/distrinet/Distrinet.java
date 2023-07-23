package com.stpg.distrinet;

import com.stpg.distrinet.models.Role;
import com.stpg.distrinet.models.ennum.ERole;
import com.stpg.distrinet.repository.RoleRepository;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;

import org.springframework.context.annotation.Bean;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

@SpringBootApplication
@EnableScheduling
@EnableAsync
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class Distrinet {

    @Autowired
    RoleRepository roleRepository;
    @PostConstruct
    public void init() throws IOException {
        if(roleRepository.count() == 0) {
            List<Role> roles = new ArrayList<>();
            Role r = new Role();
            r.setName(ERole.ROLE_USER);
            roles.add(r);
            r = new Role();
            r.setName(ERole.ROLE_ADMIN);
            roles.add(r);
            roleRepository.saveAll(roles);
        }



    }

    @Bean
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(2);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("AppIncAsync-");
        executor.initialize();
        return executor;
    }

    public static void main(String[] args) {
        SpringApplication.run(Distrinet.class, args);
    }

}
