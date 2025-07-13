# Keycloak App

## 🔐 Description

Application d'authentification et d'autorisation basée sur Keycloak, fournissant une solution complète d'Identity and Access Management (IAM) pour les applications modernes. Cette application intègre Keycloak pour offrir une authentification unique (SSO), la gestion des utilisateurs et des autorisations fine-grained.

## ✨ Fonctionnalités

- **🔑 Single Sign-On (SSO)** - Authentification unique pour toutes les applications
- **👥 Gestion des utilisateurs** - Interface complète pour la gestion des comptes utilisateurs
- **🛡️ Autorisation fine** - Contrôle d'accès basé sur les rôles et politiques
- **🌐 Fédération d'identité** - Support pour les fournisseurs d'identité externes
- **📱 Authentification sociale** - Login via Google, Facebook, GitHub, etc.
- **🔐 Authentification multi-facteurs (MFA)** - Support TOTP, WebAuthn/Passkeys
- **🔄 Protocoles standards** - OpenID Connect, OAuth 2.0, SAML 2.0
- **📊 Audit et monitoring** - Journalisation complète des événements de sécurité

## 🏗️ Architecture

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Client App    │    │   Keycloak      │    │   Backend API   │
│                 │───▶│   Server        │◀───│                 │
│                 │    │                 │    │                 │
└─────────────────┘    └─────────────────┘    └─────────────────┘
         │                       │                       │
         │                       │                       │
         ▼                       ▼                       ▼
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   User Browser  │    │   Database      │    │   External APIs │
│                 │    │   (PostgreSQL)  │    │                 │
└─────────────────┘    └─────────────────┘    └─────────────────┘
```

## 🚀 Technologies utilisées

### Backend
- **Java 17+**
- **Spring Boot 3.x**
- **Spring Security**
- **Keycloak 24.x**
- **PostgreSQL**
- **Docker & Docker Compose**

### Frontend
- **React 18**
- **TypeScript**
- **Keycloak JS Adapter**
- **Axios**
- **Material-UI / Tailwind CSS**

### DevOps
- **Docker**
- **Docker Compose**
- **Nginx**
- **Maven / npm**

## 📁 Structure du projet

```
keycloak-app/
├── backend/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/app/keycloak/
│   │   │   │   ├── config/
│   │   │   │   │   ├── KeycloakConfig.java
│   │   │   │   │   └── SecurityConfig.java
│   │   │   │   ├── controller/
│   │   │   │   │   ├── AuthController.java
│   │   │   │   │   └── UserController.java
│   │   │   │   ├── service/
│   │   │   │   │   ├── KeycloakService.java
│   │   │   │   │   └── UserService.java
│   │   │   │   └── KeycloakApplication.java
│   │   │   └── resources/
│   │   │       ├── application.yml
│   │   │       └── application-dev.yml
│   │   └── test/
│   └── pom.xml
├── frontend/
│   ├── src/
│   │   ├── components/
│   │   │   ├── Auth/
│   │   │   ├── Dashboard/
│   │   │   └── Profile/
│   │   ├── services/
│   │   │   ├── keycloak.ts
│   │   │   └── api.ts
│   │   ├── hooks/
│   │   │   └── useAuth.ts
│   │   ├── App.tsx
│   │   └── index.tsx
│   ├── package.json
│   └── tsconfig.json
├── keycloak/
│   ├── themes/
│   ├── realms/
│   │   └── realm-export.json
│   └── Dockerfile
├── docker-compose.yml
├── nginx/
│   └── nginx.conf
└── README.md
```

## ⚙️ Configuration

### Variables d'environnement

```bash
# Keycloak Configuration
KEYCLOAK_ADMIN=admin
KEYCLOAK_ADMIN_PASSWORD=admin123
KEYCLOAK_REALM=keycloak-app
KEYCLOAK_CLIENT_ID=keycloak-app-client
KEYCLOAK_CLIENT_SECRET=your-client-secret

# Database Configuration
DB_HOST=postgres
DB_PORT=5432
DB_NAME=keycloak
DB_USER=keycloak
DB_PASSWORD=password

# Application Configuration
APP_PORT=8080
API_BASE_URL=http://localhost:8080
FRONTEND_URL=http://localhost:3000
```

### Configuration Keycloak (application.yml)

```yaml
spring:
  application:
    name: keycloak-app
  security:
    oauth2:
      resourceserver:
        jwt:
          issuer-uri: http://localhost:8080/realms/keycloak-app
      client:
        registration:
          keycloak:
            client-id: ${KEYCLOAK_CLIENT_ID}
            client-secret: ${KEYCLOAK_CLIENT_SECRET}
            scope: openid,profile,email
            authorization-grant-type: authorization_code
            redirect-uri: "{baseUrl}/login/oauth2/code/{registrationId}"
        provider:
          keycloak:
            issuer-uri: http://localhost:8080/realms/keycloak-app
            user-name-attribute: preferred_username

keycloak:
  realm: ${KEYCLOAK_REALM}
  auth-server-url: http://localhost:8080
  ssl-required: external
  resource: ${KEYCLOAK_CLIENT_ID}
  credentials:
    secret: ${KEYCLOAK_CLIENT_SECRET}
  use-resource-role-mappings: true
  bearer-only: true
```

## 🛠️ Installation et démarrage

### Prérequis

- **Docker & Docker Compose**
- **Java 17+** (pour le développement local)
- **Node.js 18+** (pour le frontend)
- **Maven 3.8+**

### Démarrage rapide avec Docker

1. **Cloner le repository**
   ```bash
   git clone https://github.com/OumyL/keycloak-app.git
   cd keycloak-app
   ```

2. **Configurer les variables d'environnement**
   ```bash
   cp .env.example .env
   # Éditer le fichier .env avec vos configurations
   ```

3. **Démarrer l'ensemble de la stack**
   ```bash
   docker-compose up -d
   ```

4. **Vérifier le démarrage**
   ```bash
   # Vérifier Keycloak
   curl http://localhost:8080/health

   # Vérifier l'application
   curl http://localhost:3000
   ```

### Développement local

#### Backend

```bash
cd backend
mvn clean install
mvn spring-boot:run
```

#### Frontend

```bash
cd frontend
npm install
npm start
```

#### Keycloak (développement)

```bash
docker run -p 8080:8080 \
  -e KEYCLOAK_ADMIN=admin \
  -e KEYCLOAK_ADMIN_PASSWORD=admin \
  quay.io/keycloak/keycloak:24.0.1 \
  start-dev
```

## 🔧 Configuration Keycloak

### 1. Accès à l'administration

- **URL** : http://localhost:8080/admin
- **Username** : admin
- **Password** : admin

### 2. Création du Realm

```bash
# Importer le realm depuis le fichier de configuration
curl -X POST http://localhost:8080/admin/realms \
  -H "Authorization: Bearer $ACCESS_TOKEN" \
  -H "Content-Type: application/json" \
  -d @keycloak/realms/realm-export.json
```

### 3. Configuration du Client

```json
{
  "clientId": "keycloak-app-client",
  "enabled": true,
  "clientAuthenticatorType": "client-secret",
  "secret": "your-client-secret",
  "redirectUris": ["http://localhost:3000/*"],
  "webOrigins": ["http://localhost:3000"],
  "protocol": "openid-connect",
  "publicClient": false,
  "standardFlowEnabled": true,
  "implicitFlowEnabled": false,
  "directAccessGrantsEnabled": true
}
```

### 4. Rôles et permissions

```bash
# Créer des rôles
curl -X POST http://localhost:8080/admin/realms/keycloak-app/roles \
  -H "Authorization: Bearer $ACCESS_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"name": "user", "description": "User role"}'

curl -X POST http://localhost:8080/admin/realms/keycloak-app/roles \
  -H "Authorization: Bearer $ACCESS_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"name": "admin", "description": "Admin role"}'
```

## 📊 Endpoints API

### Endpoints d'authentification

| Endpoint | Méthode | Description |
|----------|---------|-------------|
| `/api/auth/login` | POST | Initier la connexion |
| `/api/auth/logout` | POST | Déconnexion |
| `/api/auth/refresh` | POST | Rafraîchir le token |
| `/api/auth/user-info` | GET | Informations utilisateur |

### Endpoints utilisateur

| Endpoint | Méthode | Description | Rôle requis |
|----------|---------|-------------|-------------|
| `/api/users` | GET | Liste des utilisateurs | admin |
| `/api/users/{id}` | GET | Détails utilisateur | user |
| `/api/users/{id}` | PUT | Modifier utilisateur | user |
| `/api/users/{id}/roles` | POST | Assigner rôles | admin |

### Exemples d'utilisation

```bash
# Connexion
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "user@example.com", "password": "password"}'

# Accès à une ressource protégée
curl -X GET http://localhost:8080/api/users/me \
  -H "Authorization: Bearer $ACCESS_TOKEN"
```

## 🐳 Docker

### Docker Compose complet

```yaml
version: '3.8'

services:
  postgres:
    image: postgres:15
    environment:
      POSTGRES_DB: keycloak
      POSTGRES_USER: keycloak
      POSTGRES_PASSWORD: password
    volumes:
      - postgres_data:/var/lib/postgresql/data
    networks:
      - keycloak-network

  keycloak:
    image: quay.io/keycloak/keycloak:24.0.1
    command: start
    environment:
      KC_HOSTNAME: localhost
      KC_HOSTNAME_PORT: 8080
      KC_HOSTNAME_STRICT: false
      KC_HOSTNAME_STRICT_HTTPS: false
      KC_LOG_LEVEL: info
      KC_METRICS_ENABLED: true
      KC_HEALTH_ENABLED: true
      KC_DB: postgres
      KC_DB_URL: jdbc:postgresql://postgres/keycloak
      KC_DB_USERNAME: keycloak
      KC_DB_PASSWORD: password
      KEYCLOAK_ADMIN: admin
      KEYCLOAK_ADMIN_PASSWORD: admin
    ports:
      - "8080:8080"
    depends_on:
      - postgres
    networks:
      - keycloak-network

  backend:
    build: ./backend
    ports:
      - "8081:8081"
    environment:
      KEYCLOAK_REALM: keycloak-app
      KEYCLOAK_CLIENT_ID: keycloak-app-client
      KEYCLOAK_CLIENT_SECRET: your-client-secret
    depends_on:
      - keycloak
    networks:
      - keycloak-network

  frontend:
    build: ./frontend
    ports:
      - "3000:80"
    environment:
      REACT_APP_KEYCLOAK_URL: http://localhost:8080
      REACT_APP_KEYCLOAK_REALM: keycloak-app
      REACT_APP_KEYCLOAK_CLIENT_ID: keycloak-app-client
    depends_on:
      - backend
    networks:
      - keycloak-network

volumes:
  postgres_data:

networks:
  keycloak-network:
    driver: bridge
```

## 🧪 Tests

### Tests Backend

```bash
cd backend

# Tests unitaires
mvn test

# Tests d'intégration
mvn verify

# Tests avec Testcontainers
mvn test -Dspring.profiles.active=test
```

### Tests Frontend

```bash
cd frontend

# Tests unitaires
npm test

# Tests E2E avec Cypress
npm run cypress:run

# Tests de couverture
npm run test:coverage
```

### Tests d'intégration Keycloak

```bash
# Test de connexion
npm run test:integration:auth

# Test des rôles et permissions
npm run test:integration:roles
```

## 🔒 Sécurité

### Configuration SSL/TLS

```yaml
# Production configuration
keycloak:
  hostname: your-domain.com
  hostname-strict-https: true
  https-certificate-file: /path/to/cert.pem
  https-certificate-key-file: /path/to/key.pem
```

### Politiques de sécurité

```javascript
// Frontend - Configuration Keycloak
const keycloakConfig = {
  url: 'https://keycloak.your-domain.com',
  realm: 'production-realm',
  clientId: 'secure-client',
  onLoad: 'login-required',
  checkLoginIframe: false,
  silentCheckSsoRedirectUri: window.location.origin + '/silent-check-sso.html'
};
```

### Audit et monitoring

```yaml
# Configuration des logs d'audit
logging:
  level:
    org.keycloak.events: DEBUG
    org.springframework.security: DEBUG
  pattern:
    file: "%d{ISO8601} [%thread] %-5level %logger{36} - %msg%n"
```

## 📈 Monitoring

### Métriques Keycloak

```bash
# Métriques de santé
curl http://localhost:8080/health

# Métriques Prometheus
curl http://localhost:8080/metrics
```

### Dashboard Grafana

Importez le dashboard fourni dans `monitoring/grafana-dashboard.json` pour surveiller :
- Nombre de connexions actives
- Temps de réponse d'authentification
- Échecs de connexion
- Utilisation des ressources

## 🚀 Déploiement

### Environnement de production

```bash
# Build des images de production
docker-compose -f docker-compose.prod.yml build

# Déploiement
docker-compose -f docker-compose.prod.yml up -d

# Vérification
docker-compose -f docker-compose.prod.yml ps
```

### Configuration Nginx

```nginx
server {
    listen 443 ssl;
    server_name keycloak.your-domain.com;

    ssl_certificate /path/to/cert.pem;
    ssl_certificate_key /path/to/key.pem;

    location / {
        proxy_pass http://keycloak:8080;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
    }
}
```

## 🤝 Contribution

1. **Fork** le projet
2. **Créer** une branche (`git checkout -b feature/nouvelle-fonctionnalite`)
3. **Committer** (`git commit -am 'Ajouter nouvelle fonctionnalité'`)
4. **Pousser** (`git push origin feature/nouvelle-fonctionnalite`)
5. **Créer** une Pull Request

### Standards de développement

- **Code Quality** : SonarQube analysis
- **Security** : OWASP dependency check
- **Tests** : Minimum 80% coverage
- **Documentation** : JavaDoc/JSDoc obligatoire

## 📝 Changelog

### [2.0.0] - 2024-XX-XX
- Migration vers Keycloak 24.x
- Support WebAuthn/Passkeys
- Amélioration des performances
- Nouveau theme personnalisé

### [1.1.0] - 2024-XX-XX
- Ajout authentification sociale
- Support MFA avec TOTP
- Dashboard administrateur
- API REST complète

### [1.0.0] - 2024-XX-XX
- Version initiale
- Authentification SSO
- Gestion des utilisateurs
- Intégration Spring Security

## 🐛 Dépannage

### Problèmes courants

**Keycloak ne démarre pas**
```bash
# Vérifier les logs
docker-compose logs keycloak

# Vérifier la base de données
docker-compose exec postgres psql -U keycloak -d keycloak -c "SELECT version();"
```

**Erreur CORS**
```javascript
// Vérifier la configuration des origines web dans Keycloak
// Admin Console > Clients > Settings > Web Origins
```

**Token expiré**
```javascript
// Configuration du refresh automatique
keycloak.onTokenExpired = () => {
  keycloak.updateToken(30);
};
```

## 📚 Documentation

- [Keycloak Documentation](https://www.keycloak.org/documentation)
- [Spring Security OAuth2](https://spring.io/projects/spring-security-oauth)
- [Keycloak JS Adapter](https://www.keycloak.org/docs/latest/securing_apps/index.html#_javascript_adapter)
- [API Documentation](http://localhost:8080/swagger-ui.html)

## 📞 Support

- **Issues GitHub** : [Créer une issue](https://github.com/OumyL/keycloak-app/issues)
- **Discussions** : [GitHub Discussions](https://github.com/OumyL/keycloak-app/discussions)
- **Security** : security@yourcompany.com

## 📄 Licence

Ce projet est sous licence MIT. Voir le fichier [LICENSE](LICENSE) pour plus de détails.

---

**Développé avec 🔐 par [OumyL](https://github.com/OumyL)**
