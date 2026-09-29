package api_teste.ds.configs; // Define o pacote onde a classe de configuração está localizada

import org.springframework.context.annotation.Configuration; // Importa a anotação que identifica uma classe de configuração do Spring
import org.springframework.web.servlet.config.annotation.CorsRegistry; // Importa a classe responsável por configurar as regras de CORS
import org.springframework.web.servlet.config.annotation.EnableWebMvc; // Importa a anotação que habilita o Spring MVC
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer; // Importa a interface que permite personalizar o Spring MVC

@Configuration // Informa ao Spring que esta classe contém configurações da aplicação
@EnableWebMvc // Habilita os recursos do Spring MVC
public class CorsConfig implements WebMvcConfigurer { // Cria a classe CorsConfig e permite personalizar o comportamento do Spring MVC

    @Override // Indica que o método abaixo está sobrescrevendo um método da interface WebMvcConfigurer
    public void addCorsMappings(CorsRegistry registry) { // Cria o método responsável por configurar as regras de CORS

        registry.addMapping("/**") // Permite aplicar as regras de CORS para todas as rotas da API
                .allowedOrigins("*") // Permite que requisições sejam feitas por qualquer origem
                .allowedMethods("GET", "POST", "PUT", "DELETE"); // Permite os métodos GET, POST, PUT e DELETE
    } // Fecha o método addCorsMappings
} // Fecha a classe CorsConfig
