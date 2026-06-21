# Mural REST API

API REST do mural 

* Swagger: http://localhost:8080/swagger-ui/index.html
* Login: `POST /api/auth/login` com `{"username":"admin","password":"admin"}` ou `{"username":"user","password":"user"}`
* Cadastro publico: `POST /api/user`
* Mensagens autenticadas: `GET /api/messages`, `GET /api/messages/{id}`, `POST /api/messages`, `DELETE /api/messages/{id}`
