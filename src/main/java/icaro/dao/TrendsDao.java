package icaro.dao;

import icaro.api.Results;
import icaro.api.Trends;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TrendsDao {

    Trends dataBase;

    public TrendsDao() {
        this.dataBase = new Trends();
        readFile();

    }

    //CREATE
    public void createResult(Results result){
        this.dataBase.getResults().add(result);
        writeFile();
    }

    //READ
    public Trends getAllTrends() {
        return this.dataBase;
    }

    //UPDATE
    public void updateResult(Results result){
        for(int i=0; i<this.dataBase.getResults().size(); i++) {
            if (this.dataBase.getResults().get(i).getDate().equals(result.getDate())) {
                this.dataBase.getResults().get(i).setValue(result.getValue());
                //this.dataBase.getResults().set(i, result);
            }
        }
        writeFile();
    }

    //DELETE
    public void deleteResult(Results result){
        for(int i=0; i<this.dataBase.getResults().size(); i++) {
            if (this.dataBase.getResults().get(i).getDate().equals(result.getDate())) {
                this.dataBase.getResults().get(i).deleteValue(result.getValue());
            }
        }
        writeFile();
    }



//FUNÇÕES AUXILIARES

// Lê o arquivo csv e instancia a variável database
public void readFile(){
    System.out.println("TrendsDAO - Lendo dados do arquivo CSV");

    try (Scanner scanner = new Scanner(new File("./resources/multiTimeline.csv"));) {

        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] cols = line.split(",");
            Date week = null; // usado para testar se a linha começa com uma data

            try {
                week = sdf.parse(cols[0]);
            } catch(Exception e) { /*Não é uma data*/ }

            // Lendo dados
            if (week != null) {
                Results results = new Results();
                results.setDate(cols[0]);
                results.setValue(Double.parseDouble(cols[1]));
                this.dataBase.getResults().add(results);

                // Lendo cabecalho
            }  else if(cols.length > 0 && (cols[0].equals("Tempo") || cols[0].equals("Hora"))) {
                this.dataBase.setTerm(cols[1]);
            }
        }

        System.out.println("TrendsDAO - Leitura realizada");
    } catch (Exception ex) {
        this.dataBase = new Trends();
        ex.printStackTrace();
        System.out.println("TrendsDAO - Erro na leitura do CSV");
    }
}

    public void writeFile(){
        System.out.println("TrendsDAO - Escrevendo no arquivo CSV");

        String str = "Categoria: Todas as categorias\n\nTempo,";
        str += this.dataBase.getTerm() + "\n";

        List<Results> results =  this.dataBase.getResults();
        for( int i=0; i<results.size(); i++){
            Results r = results.get(i);
            str += r.getDate() + "," + r.getValue() + "\n";
        }

        try {
            FileWriter writer = new FileWriter("./resources/multiTimeline.csv");
            writer.write(str);
            writer.close();
            System.out.println("TrendsDAO - Dados escritos no arquivo com sucesso!");
        } catch (IOException e) {
            System.out.println("TrendsDAO - Erro na escrita do CSV");
            e.printStackTrace();
        }
    }
}
