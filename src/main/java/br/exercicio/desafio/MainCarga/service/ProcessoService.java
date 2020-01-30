package br.exercicio.desafio.MainCarga.service;

import br.exercicio.desafio.MainCarga.entity.ClienteEntity;
import br.exercicio.desafio.MainCarga.entity.ItemDeVendaEntity;
import br.exercicio.desafio.MainCarga.entity.VendaEntity;
import br.exercicio.desafio.MainCarga.entity.VendedorEntity;
import br.exercicio.desafio.MainCarga.repository.ClienteRepository;
import br.exercicio.desafio.MainCarga.repository.ItemDeVendaRepository;
import br.exercicio.desafio.MainCarga.repository.VendaRepository;
import br.exercicio.desafio.MainCarga.repository.VendedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class ProcessoService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private VendedorRepository vendedorRepository;

    @Autowired
    private ItemDeVendaRepository itemDeVendaRepository;

    public void executaProcesso(Path diretorioIn) throws IOException {
        limparBase();

        Stream<Path> list = Files.list(diretorioIn);
        list.forEach(file -> {

            try {
                Files.lines(file).forEach(this::lendoLinha);
            } catch (Exception e) {
                System.out.println("Erro ao ler o arquivo " + file);
                e.printStackTrace();
            }
        });

        concluirTarefa();
    }

    private void concluirTarefa() throws IOException {
        String pathName = System.getProperty("user.home") + File.separator + "data" + File.separator + "out";
        Path path = Paths.get(pathName);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
        path = Paths.get(pathName + File.separator + "Result.txt");
        if (Files.exists(path)) {
            Files.delete(path);
        }
        Files.createFile(path);
        StringBuilder stringResult = new StringBuilder();
        stringResult.append(" - Quantidade de clientes no arquivo de entrada: ").append(clienteRepository.count());
        stringResult.append("\n - Quantidade de vendedores no arquivo de entrada: ").append(vendedorRepository.count());
        stringResult.append("\n - ID da venda mais cara: ");
        Optional.ofNullable(vendaRepository.findVendaMaisCara()).ifPresent(longs -> {
            if (longs.size() > 0) {
                stringResult.append(longs.get(0));
            }
        });
        stringResult.append("\n - O pior vendedor: ");
        Optional.ofNullable(vendedorRepository.piorVendedor()).ifPresent(vendedorEntities -> {
            if (vendedorEntities.size() > 0) {
                stringResult.append(Optional.ofNullable(vendedorEntities.get(0)).map(VendedorEntity::getName).orElse(""));
            }
        });
        Files.write(path, stringResult.toString().getBytes());

        System.out.println("[ -- GERADO ARQUIVO DOS RESULTADOS '" + path + "'--]");
    }

    private void exibirRegistros() {

        System.out.println(clienteRepository.findAll());
        System.out.println(vendaRepository.findAll());
        System.out.println(vendedorRepository.findAll());
        System.out.println(itemDeVendaRepository.findAll());
    }

    private void lendoLinha(String linha) {
        if (linha != null) {
            if (linha.startsWith("001")) {
                criarVendedor(linha);
            } else if (linha.startsWith("002")) {
                criarCliente(linha);
            } else if (linha.startsWith("003")) {
                criarVenda(linha);
            }
        }
    }

    private void limparBase() {
        clienteRepository.deleteAll();
        vendaRepository.deleteAll();
        vendedorRepository.deleteAll();
        itemDeVendaRepository.deleteAll();
    }

    private void criarVenda(String linha) {
        String[] split = linha.split("ç");

        VendedorEntity vendedorEntity = vendedorRepository.findByName(split[3]);

        VendaEntity vendaEntity = new VendaEntity();
        vendaEntity.setSaleID(Long.valueOf(split[1]));
        vendaEntity.setValorTotal(gravarItensDeVenda(Long.valueOf(split[1]), split[2]));
        vendaEntity.setIdVendedor(vendedorEntity.getId());
        vendaEntity.setSalesmanName(split[3]);

        vendaRepository.save(vendaEntity);

        vendedorEntity.setValorTotal(vendaRepository.somaPorVendedor(vendedorEntity.getId()));
        vendedorRepository.save(vendedorEntity);
    }

    private Double gravarItensDeVenda(Long idVenda, String itens) {
        Double total = 0.00;
        itens = itens.replaceAll("\\[", "");
        itens = itens.replaceAll("]", "");
        String[] itemSlit = itens.split(",");
        for (String item : itemSlit) {
            String[] split = item.split("-");
            ItemDeVendaEntity itemDeVendaEntity = new ItemDeVendaEntity();
            itemDeVendaEntity.setSaleID(idVenda);
            itemDeVendaEntity.setItemID(Long.valueOf(split[0]));
            itemDeVendaEntity.setQuantity(Long.valueOf(split[1]));
            itemDeVendaEntity.setPrice(Double.valueOf(split[2]));
            itemDeVendaEntity.setValorTotal(itemDeVendaEntity.getPrice() * itemDeVendaEntity.getQuantity());
            total += itemDeVendaEntity.getValorTotal();
            itemDeVendaRepository.save(itemDeVendaEntity);
        }

        return total;
    }

    private void criarCliente(String linha) {
        String[] split = linha.split("ç");
        ClienteEntity clienteEntity = new ClienteEntity();
        clienteEntity.setCnpj(split[1]);
        clienteEntity.setName(split[2]);
        clienteEntity.setBusinessArea(split[3]);

        clienteRepository.save(clienteEntity);
    }

    private void criarVendedor(String linha) {
        String[] split = linha.split("ç");
        VendedorEntity vendedorEntity = new VendedorEntity();
        vendedorEntity.setCpf(split[1]);
        vendedorEntity.setName(split[2]);
        vendedorEntity.setSalary(Double.parseDouble(split[3]));
        vendedorEntity.setValorTotal(0.00);

        vendedorRepository.save(vendedorEntity);
    }

}
