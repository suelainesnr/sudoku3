# Sudoku3

Este é um projeto de jogo de Sudoku desenvolvido em Java, utilizando a biblioteca Swing para a interface gráfica. O projeto foi estruturado com foco em boas práticas de programação, separação de responsabilidades e uma cobertura abrangente de testes unitários, sendo ideal para estudo de desenvolvedores juniores.

## 🚀 Tecnologias Utilizadas

- **Java 17**: Linguagem principal do projeto.
- **Maven**: Gerenciamento de dependências e build.
- **JUnit 5**: Framework para a execução de testes automatizados.
- **Swing**: Biblioteca para criação da interface gráfica (GUI).

## ✨ Funcionalidades

- **Interface Gráfica**: Tabuleiro interativo de 9x9 com destaque para blocos 3x3.
- **Validação em Tempo Real**: O jogo impede ou alerta sobre jogadas inválidas (números repetidos em linhas, colunas ou blocos).
- **Células Fixas**: Números iniciais do puzzle são protegidos e não podem ser alterados pelo jogador.
- **Status do Jogo**: Detecção automática de vitória (Resolvido), erros (Inválido) ou progresso (Incompleto).
- **Geração de Puzzles**: Carregamento automático de desafios aleatórios através do `PuzzleLoader`.
- **Modo CLI**: Possibilidade de iniciar o jogo com um puzzle customizado via argumentos de linha de comando.
- **Limpeza do Tabuleiro**: Opção de resetar as jogadas do usuário mantendo os números fixos originais.

## 📁 Estrutura do Projeto

- `src/main/java/org/example/`:
    - `SudokuGame.java`: Ponto de entrada da aplicação.
    - `Cell.java`: Representação detalhada de uma célula (valores fixos, do jogador e rascunhos).
    - `domain/`: Contém a lógica de negócio do jogo (`SudokuBoard`, enums de status).
    - `service/`: Serviços auxiliares, como o `PuzzleLoader`.
    - `ui/`: Componentes da interface gráfica e filtros de entrada.
- `src/test/java/`: Suite de testes completa cobrindo todas as camadas do sistema.

## 🛠️ Como Executar

### Pré-requisitos
- JDK 17 ou superior.
- Maven instalado e configurado no PATH.

### Executando a Aplicação
Para rodar o jogo com o puzzle padrão:
```bash
mvn compile exec:java -Dexec.mainClass="org.example.SudokuGame"
```

Para rodar com um puzzle customizado (passando row,col,value):
```bash
mvn compile exec:java -Dexec.mainClass="org.example.SudokuGame" -Dexec.args="0,0,5 1,1,3"
```

## 🧪 Como Executar os Testes

O projeto conta com mais de 30 testes unitários que garantem a integridade das regras do Sudoku e do comportamento da interface.

Para executar todos os testes:
```bash
mvn test
```

## 📝 Observações para Desenvolvedores

- **Comentários**: Todos os arquivos de teste foram exaustivamente comentados em português para facilitar o entendimento da lógica de verificação.
- **Extensibilidade**: A classe `Cell` está preparada para suportar sistemas de "rascunho" (drafts), permitindo futuras expansões na UI.
- **Refatoração**: O código passou por processos de refatoração para garantir testabilidade, especialmente na separação entre lógica de inicialização e inicialização de componentes Swing.
