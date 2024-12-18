# # Tech Challenger - FASE 3 🚀🚀
Projeto do Tech Chalenge da Pós Tech FIAP curso Arquitetura e Desenvolvimento Java Fase 3


# Guia de Execução de Testes e Geração de Relatórios

Este guia descreve como executar os testes unitários e de integração do projeto, bem como como gerar relatórios detalhados utilizando **Allure** e **JaCoCo**.

### Passo a Passo para Executar os Testes
1. Abra o terminal no seu computador.
2. Navegue até o diretório raiz do projeto utilizando o comando:
   ```bash
   cd /tech-challenge-fase3
   ```
3. Execute todos os testes do projeto com o seguinte comando:
   ```bash
   mvn test
   ```
   Esse comando irá rodar tanto os testes unitários quanto os de integração configurados no projeto.

## Relatório Allure
O **Allure** é usado para gerar relatórios detalhados da execução dos testes.

### Passo a Passo para Visualizar o Relatório Allure

1. Certifique-se de que o **Allure** esteja instalado em seu sistema.
2. Após rodar os testes, gere e abra o relatório no navegador com o seguinte comando:
   ```bash
   allure open target/allure-report
   ```
3. Caso queira servir o relatório diretamente no navegador em um servidor local, use:
   ```bash
   allure serve target/allure-results
   ```

## Relatório JaCoCo
O **JaCoCo** é usado para medir a cobertura de testes no código fonte.

### Passo a Passo para Gerar o Relatório JaCoCo
1. Certifique-se de que o plugin **JaCoCo** está configurado no arquivo `pom.xml` do projeto.
2. Para gerar o relatório de cobertura, execute o seguinte comando:
   ```bash
   mvn test jacoco:report
   ```
3. Após a execução, o relatório será gerado no formato HTML no seguinte diretório:
   ```
   target/site/jacoco/index.html
   ```
4. Abra o arquivo `index.html` no navegador para visualizar o detalhamento da cobertura dos testes.

## Conclusão
Seguindo este passo a passo, você consegue executar os testes do projeto, gerar relatórios detalhados e analisar tanto os resultados da execução quanto a cobertura do código. Isso é fundamental para garantir a qualidade e a confiabilidade do software.
