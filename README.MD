# 🔐 Controle de Acesso — Spring Boot + Spring Security

Projeto didático para treinar **autenticação e autorização** com Spring Security, incluindo:
- **HTTP Basic** e **Form Login**
- **Method Security** com `@PreAuthorize`
- `AuthenticationProvider` custom (ex.: “senha master”)
- **Filtro customizado** (`x-secret`) que injeta autenticação no `SecurityContext`
- **Usuário/Grupo/Permissões** com persistência (H2/JPA)

---

## 📌 Objetivo

Entender, na prática, como o Spring Security:
1) autentica usuários (Basic/Form/providers custom)
2) popula o `SecurityContext`
3) valida permissões (roles/authorities) para liberar endpoints

---

## 🧰 Tecnologias

- Java 17+
- Spring Boot
- Spring Security
- Spring Web
- Spring Data JPA
- H2 Database
- Lombok

---

## ▶️ Como executar

### Pré-requisitos
- Java 17+

### Rodar localmente
```bash
./mvnw spring-boot:run
# Windows:
mvnw.cmd spring-boot:run