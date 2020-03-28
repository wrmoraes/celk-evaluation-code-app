# CELK APP - CADASTROS - AVALIAÇÃO by Wmoraes

Bem vindo.

Este app foi construído para a avaliação de desenvolimento na empresa CELK SISTEMAS.

**Como justificativa para o desenvolvimento temos:**

> A equipe de atendimento precisa informar no cadastro de cada paciente a unidade
> federativa. Porém, previamente, será necessário cadastrar os estados.

**Como história de usuário:**

> Eu como administrador do sistema desejo cadastrar os estados no sistema para
> que esses dados sejam usados posteriormente pelos atendentes no cadastro de
> pacientes.
>
> Critérios de aceite:
>
> 1.  Cadastrar uma unidade federativa por vez com data e hora do
>     registro;
> 2.  Apresentar a lista das unidades federativas na mesma tela,
>     abaixo do cadastro;
> 3.  Permitir o nome completo e sigla das unidades federativas com
>     data e hora atualizadas;

## Desenvolvimento

Após as definições do Escopo tratadas no dia 25/03/2020 ás 12:00, iniciei o desenvolvimento. Inicialmente a minha ideia foi contruir uma estrutura provedora de serviços via REST, sendo está aplicação a fonte que
seria utilizadas posteriormente pelo Frontend para as ações.

Com isso desenvolvi o seguinte projeto: https://github.com/wrmoraes/celk-evaluation-code-webservice-DEPRECATED-, gastando mais ou menos 11 Horas para tal. Nele está previsto uma estrutura básica de autenticação
via Token, além dos serviços de CRUD para os usuários do serviço e as Unidades Federativas solicitadas pelo escopo inicial.

Porém por um imprevisto no dia 26/03/2020. Acabei optando por abandonar a ideia inicial de montar toda a estrutura "manualmente" e parti para uma ideologia de construção da aplicação SMART. Optando por utilizar a
ferramenta [JHipster V6.8.0](https://www.jhipster.tech/documentation-archive/v6.8.0) de modo a criar o "Bootstrap" da minha Stack, sendo que tal ferramenta oferece de forma totalmente automatizada a construção das configurações, relações de autenticação e criação do FrontEnd,
tudo isso sem perder os padrões de projetos mais recentes além da estruturação de todos os testes necessários e estrutura customizada de administraçao dos recursos da aplicação. Sendo assim, customizei a aplicação
para se adequar as definições em mais ou menos 10 Horas.

Considero que a aplicação está dentro dos parâmetros solicitados, nas não finalizada. Como em todo desenvolvimento ainda existem as melhorias que por questão de tempo hábil, ficarão como indicações futuras
na seção de **TODO** que será especificada posteriormente no documento.

**Para ver a aplicação rodando acesse: <https://celk-app-evaluation-code.herokuapp.com/>

**Para acessar a documentação da API, basta logar na aplicação como administrador e acessar: <https://celk-app-evaluation-code.herokuapp.com/admin/docs>

Como diz uma frase muito usada na programação :

> Não é necessário reiventar a roda para contruir um carro potente!

### Orientações para deploy e uso no ambiente de Desenvolvimento

Antes de inciar o processo de Buld da aplicação, você deve instalar e configurar as seguintes dependências em seu ambiente:

1. [Node.js][]: Utilizamos o Node para rodar um servidor web e buildar o projeto.
   Dependendo do seu sistema, você pode instalar o Node direto da fonte ou de um pacote pré-empacotado.

Após instalar o Node, você poderá executar o seguinte comando para instalar as ferramentas de desenvolvimento.
Você só necessitará executar este comando quando as dependências mudarem em [package.json](package.json).

    npm install

Foi utilizado scripts npm e [Webpack][] como nosso compilador.

Rode os seguintes comandos em dois terminais separados para criar uma experiência de desenvolvimento mais otimizada onde o navegador
atualizará de forma automática quando os arquivos do frontend forem modificados em sua IDE ou na pasta.

    ./mvnw
    npm start

Npm também é utilizado para gerenciar as dependências de CSS e JavaScript usadas na aplicação. Você pode atualizar as dependências
especificando uma versão mais recente no arquivo [package.json](package.json). Você também pode rodar os comandos 'npm update' e 'npm install' para gerenciar as dependências.
Adicione a palavra 'help' antes de qualquer comando para saber como utilizar. Por exemplo, 'npm help update'.

O comando `npm run` irá listar todos os scripts disponíveis a serem executados neste projeto.

### Suporte PWA

Esta aplicação tem suporte para ser executada como aplicação PWA.
Porém esta funcionalidade está desabilitada como padrão. Se deseja habilitar, descomente o seguinte código no arquivo `src/main/webapp/index.html`:

```html
<script>
  if ('serviceWorker' in navigator) {
    navigator.serviceWorker.register('./service-worker.js').then(function() {
      console.log('Service Worker Registered');
    });
  }
</script>
```

Nota: [Workbox](https://developers.google.com/web/tools/workbox/) foi utilizado para gerar dinamicamente o arquivo `service-worker.js`.

### Usando o Angular CLI

Você pode usar o [Angular CLI][] para gerar códigos customizados na aplicação cliente.

Por exemplo, o seguinte comando:

    ng generate component meu-novo-componente

irá gerar os seguintes arquivos:

    create src/main/webapp/app/meu-novo-componente/meu-novo-componente.component.html
    create src/main/webapp/app/meu-novo-componente/meu-novo-componente.component.ts
    update src/main/webapp/app/app.module.ts

## Orientações para deploy e uso no ambiente de Desenvolvimento

### Empacotando a aplicação como Jar

Para construi o pacote jar final e otimizar a aplicação celk para produção, rode:

    ./mvnw -Pprod clean verify

Isso irá concatenar e minificar os arquivos CSS e JavaScript do cliente. Isso também irá modificar o arquivo `index.html` para referenciar estes novos arquivos.

Para se certificar que tudo isso funcionou, rode:

    java -jar target/*.jar

Então entre na aplicação [http://localhost:8080](http://localhost:8080) pelo seu browser.

### Empacotando a aplicação como war

Para empacotar a aplicação como war para implementá-lo em um servidor de aplicativos, execute:

    ./mvnw -Pprod,war clean verify

## Testando a aplicação

Para rodar os testes da aplicação, rode:

    ./mvnw verify

### Testes do cliente

Os testes unitários são executados pelo [Jest][] e escritos com [Jasmine][]. Eles estão localizados em [src/test/javascript/](src/test/javascript/) e podem ser exeutados com:

    npm test

### Qualidade de Código

O Sonar foi utilizado como analizados de qualidade de código. Você pode iniciar um servidor Sonar local(ficará disponívem em http://localhost:9001) com o comando:

```
docker-compose -f src/main/docker/sonar.yml up -d
```

Você pode rodar as análises do sonar utilizando o [sonar-scanner](https://docs.sonarqube.org/display/SCAN/Analyzing+with+SonarQube+Scanner) ou utilizando o plugin do maven.

Então, para rodar a análise do sonar utilize:

```
./mvnw -Pprod clean verify sonar:sonar
```

Se você deseja rodar novamente a fase do Sonar, certifique-se de especificar pelo menos a fase 'initialize', pois as propriedades do Sonar são carregadas do arquivo sonar-project.properties.

```
./mvnw initialize sonar:sonar
```

## Utilizando Docker para simplificar o desenvolvimento

Você pode utilizar o Docker para facilitar seus testes e deploy. Vários arquivos de docker-compose estão disponíveis na pasta [src/main/docker](src/main/docker) para subir os recursos necessários para a aplicação.

Por exemplom para iniciar uma base postgresql em um container Docker, rode:

    docker-compose -f src/main/docker/postgresql.yml up -d

Para parar e remover o container, rode:

    docker-compose -f src/main/docker/postgresql.yml down

You can also fully dockerize your application and all the services that it depends on.
É possível também dockerizar a aplicação completa e todos os serviços dependentes.
Para fazer isso, primeiro é necessário buildar a imagem da aplicação rodando:

    ./mvnw -Pprod verify jib:dockerBuild

Então rode:

    docker-compose -f src/main/docker/app.yml up -d

## TODO\*\*

Ficou interessado na ferramenta que utilizei?
Conheça ela em [jhipster]: https://www.jhipster.tech
