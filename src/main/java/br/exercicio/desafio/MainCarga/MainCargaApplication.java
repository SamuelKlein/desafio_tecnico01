package br.exercicio.desafio.MainCarga;

import br.exercicio.desafio.MainCarga.service.ProcessoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

@SpringBootApplication
public class MainCargaApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(MainCargaApplication.class, args);
	}

	@Autowired
	private ProcessoService processoService;

	@Bean
	public CommandLineRunner demo() {
		System.out.println("[ -- INICIALIZADO PROCESSO -- ]");
		return (args) -> {
			WatchService watchService
					= FileSystems.getDefault().newWatchService();

			String pathName = System.getProperty("user.home") + File.separator + "data" + File.separator + "in";
			Path path = Paths.get(pathName);
			if (!Files.exists(path)) {
				Files.createDirectories(path);
			}

			processoService.executaProcesso(path);

			path.register(
					watchService,
					StandardWatchEventKinds.ENTRY_CREATE,
					StandardWatchEventKinds.ENTRY_DELETE,
					StandardWatchEventKinds.ENTRY_MODIFY);

			WatchKey key;
			while ((key = watchService.take()) != null) {
				for (WatchEvent<?> event : key.pollEvents()) {
					System.out.println("[ -- ARQUIVOS ALTERADOR DA PASTA " + pathName + "-- ]");
					processoService.executaProcesso(path);
				}
				key.reset();
			}
		};
	}

}
