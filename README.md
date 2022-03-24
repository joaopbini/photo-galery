[Overview de autenticação Google Cloud](https://cloud.google.com/docs/authentication)

[Autenticando com uma conta de serviço](https://cloud.google.com/docs/authentication/production)

[Cloud Build Configs](https://cloud.google.com/build/docs/build-config-file-schema)

###Deploy 
`gcloud builds submit`


## Passos

- Criar projeto no Spring Initializr
    - Maven, Kotlin, Java 11
    - Spring Web
    - GCP Support
    - GCP Storage
- Criar API Photo
    - Teste da api
    - Inserir swagger
    - Importar datastore
    - Criar Photo (id, descricao)
    - Get Photo (by id)
    - Colocar upload de arquivo no criar photo
        - Registrar no storage
        - Inserir fileId na entidade Photo
    - Get URL da imagem por id de foto
- Google Vision
    - Importar biblioteca
    - Analisar imager no create
    - Salvar no campo labels
- Buscar na API imagens por label
- Em caso de erro configurar ExceptionHandler
- Configurar PORT no properties
- Criar Dockerfile
- Criar cloudbuild.yaml
- Deploy na cloud
- Acessar na cloud

`mvn package`

`docker build -t imagem-tdc .`

`docker run -p 8080:8080 imagem-tdc`



