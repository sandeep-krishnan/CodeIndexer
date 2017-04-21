package com.codeindexer;

import com.codeindexer.indexer.SourceCodeIndexer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

/**
 * Entry point for spring boot
 *
 */

@SpringBootApplication
public class App 
{
    @Autowired
    private Environment env;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    /**
     * This method is called during startup
     * The contents of the source-code directory is indexed
     * @param ctx
     * @return
     */
    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            long startTime = System.currentTimeMillis();
            String sourceCodeLocation = env.getProperty("sourcecode.location");
            System.out.println("source code location is " + sourceCodeLocation);
            SourceCodeIndexer indexer = new SourceCodeIndexer(sourceCodeLocation);
            indexer.index();
            System.out.println("Indexing finished in "
                         + (System.currentTimeMillis() - startTime)
                         + " ms... ");
        };
    }
}
