TESTES UNITARIOS - PETSHOP

Os testes foram criados em JUnit 4.

Cobertura:
- ConexaoSQLite
- TutorDTO
- PetDTO
- ServicoDTO
- FuncionarioDTO
- AgendamentoDTO
- TutorDAO
- PetDAO
- ServicoDAO
- FuncionarioDAO
- AgendamentoDAO

Os testes dos DTOs verificam construtores, getters e setters.
Os testes dos DAOs verificam cadastro, alteração, exclusão, listagem,
busca, login e regras específicas de cada DAO.
O AgendamentoDAO também é testado para horário ocupado e agendamento
cancelado.

IMPORTANTE:
Os testes dos DAOs usam o banco SQLite configurado pelo projeto
(petshop.db). Os dados usados pelos testes recebem identificadores
únicos e são removidos ao final de cada teste.

ANTES DE EXECUTAR:
1. Abra o projeto no NetBeans.
2. Garanta que o SQLite JDBC já esteja nas Libraries do projeto.
3. Adicione a biblioteca JUnit 4 ao projeto:
   botão direito em Test Libraries -> Add Library -> JUnit 4.
4. Execute Test Package ou clique com o botão direito no projeto
   e escolha Test.

As telas Swing (VIEW) não foram transformadas em testes unitários,
porque seus métodos principais são handlers privados de interface.
Para elas, o ideal é fazer testes funcionais/integrados depois.
