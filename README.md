# SGP - Sistema de Gestão da Produção

## 📌 Descrição
O SGP (Sistema de Gestão da Produção) é uma aplicação desktop desenvolvida em Java,
voltada ao cadastro, consulta e acompanhamento da produção por setor.

O sistema possui controle de acesso por usuário, com liberação de funcionalidades
de acordo com o perfil do usuário.

## 🛠 Tecnologias Utilizadas
- Java 8 (compatível com Java 21 em desenvolvimento)
- Java Swing (interface gráfica)
- Maven (gerenciamento de dependências)
- Microsoft Access (banco de dados)
- Git / GitHub (versionamento)

## 📐 Padrões do Projeto
- Código-fonte escrito em inglês
- Interface gráfica em português
- Separação por camadas:
  - UI (Swing)
  - Service (regras de negócio)
  - DAO (acesso a dados)

## 📂 Estrutura de Pacotes (resumo)
- auth → autenticação e login
- ui → telas principais
- production → controle de produção
- product → cadastro de produtos
- user → usuários e permissões
- util → utilitários e conexão com banco

## 🔐 Autenticação
O sistema possui tela de login com validação de usuário e senha.
O acesso às funcionalidades será controlado conforme o perfil do usuário.

## 🚀 Como Executar o Projeto
1. Clonar o repositório
2. Importar o projeto como Maven no Eclipse
3. Executar a classe `Main`

## 📌 Status do Projeto
🚧 Em desenvolvimento 🚧

## 📄 Documentação
A documentação detalhada do projeto encontra-se na pasta `/docs`.
