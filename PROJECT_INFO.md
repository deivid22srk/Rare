# Rare Launcher - Android Port

## 📱 Sobre o Projeto

Este é um port inicial do Rare Launcher para Android, desenvolvido em Kotlin. O aplicativo permite que os usuários façam login em suas contas Epic Games e visualizem sua biblioteca real de jogos diretamente no celular.

## ✨ Funcionalidades Implementadas

### ✅ Autenticação Epic Games
- Login seguro via WebView
- Fluxo OAuth2 completo
- Armazenamento seguro de tokens com SharedPreferences
- Renovação automática de sessão

### ✅ Biblioteca de Jogos
- Visualização da biblioteca real da Epic Games
- Carregamento paginado de todos os jogos
- Exibição de informações:
  - Título do jogo
  - Desenvolvedor
  - Namespace
  - Imagem de capa
- Pull-to-refresh para atualizar a lista
- Layout Material Design moderno

## 🛠️ Tecnologias Utilizadas

- **Linguagem**: Kotlin 100%
- **Min SDK**: 24 (Android 7.0)
- **Target SDK**: 34 (Android 14)

### Bibliotecas
- **Retrofit 2.9.0**: Cliente HTTP para chamadas de API
- **OkHttp 4.12.0**: Cliente HTTP com logging
- **Gson**: Conversão JSON
- **Coroutines**: Programação assíncrona
- **Material Components**: UI moderna
- **Glide 4.16.0**: Carregamento de imagens
- **ViewBinding**: Binding de views type-safe

## 🔐 Credenciais da API

```kotlin
Client ID: 34a02cf8f4414e29b15921876da36f9a
Client Secret: daafbccc737745039dffe53d94fc76cf
```

## 🌐 Endpoints da API

### OAuth
- Base URL: `https://account-public-service-prod03.ol.epicgames.com/`
- Endpoint de autenticação: `/account/api/oauth/token`

### Biblioteca
- Base URL: `https://library-service.live.use1a.on.epicgames.com/`
- Endpoint da biblioteca: `/library/api/public/items`

## 📂 Estrutura do Projeto

```
com.rare.launcher/
├── api/
│   ├── EpicGamesApi.kt          # Interface Retrofit
│   └── RetrofitClient.kt        # Cliente HTTP
├── model/
│   ├── AuthResponse.kt          # Modelo de autenticação
│   └── LibraryResponse.kt       # Modelo da biblioteca
├── ui/
│   ├── MainActivity.kt          # Activity de splash
│   ├── LoginActivity.kt         # Activity de login
│   ├── LibraryActivity.kt       # Activity da biblioteca
│   └── LibraryAdapter.kt        # Adapter do RecyclerView
├── utils/
│   ├── Constants.kt             # Constantes da aplicação
│   └── PrefsManager.kt          # Gerenciador de preferências
└── RareApplication.kt           # Application class
```

## 🚀 Como Compilar

### Pré-requisitos
- JDK 17 ou superior
- Android SDK com API 34
- Gradle 8.2

### Compilar Debug
```bash
./gradlew assembleDebug
```

O APK será gerado em: `app/build/outputs/apk/debug/app-debug.apk`

### Compilar Release
```bash
./gradlew assembleRelease
```

## 🤖 GitHub Actions

O projeto inclui um workflow do GitHub Actions (`.github/workflows/build.yml`) que:
- Compila automaticamente em push/pull request
- Gera APK debug
- Faz upload do APK como artifact

## 📝 Permissões Necessárias

```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```

## 🎨 Design

- **Tema**: Dark mode com cores da Epic Games
- **Cor primária**: Epic Blue (#0078F2)
- **Background**: Epic Dark (#121212)
- **Material Design 3** com componentes modernos

## 📸 Activities

1. **MainActivity**: Tela de splash com verificação de login
2. **LoginActivity**: WebView para login na Epic Games
3. **LibraryActivity**: Lista de jogos com RecyclerView

## 🔄 Fluxo de Autenticação

1. Usuário abre o app
2. MainActivity verifica se há token salvo
3. Se não houver, redireciona para LoginActivity
4. LoginActivity abre WebView com URL de login da Epic
5. Usuário faz login
6. App captura o authorization code da URL de redirect
7. Faz requisição POST para obter access_token
8. Salva tokens no SharedPreferences
9. Redireciona para LibraryActivity

## 📚 Fluxo de Biblioteca

1. LibraryActivity obtém access_token salvo
2. Faz requisição GET para endpoint da biblioteca
3. Implementa paginação (cursor-based)
4. Carrega todas as páginas de jogos
5. Exibe em RecyclerView com imagens carregadas via Glide

## 🔮 Próximos Passos Sugeridos

- [ ] Implementar detalhes do jogo ao clicar
- [ ] Adicionar busca/filtro de jogos
- [ ] Implementar download de jogos (se possível)
- [ ] Adicionar suporte a DLCs
- [ ] Implementar cache de imagens
- [ ] Adicionar tela de perfil do usuário
- [ ] Implementar refresh token automático
- [ ] Adicionar animações e transições
- [ ] Suporte a tablets e landscape
- [ ] Testes unitários e instrumentados

## 📄 Licença

GPL-3.0 License - Baseado no projeto Rare Launcher

## ⚠️ Disclaimer

Este é um aplicativo não oficial e não é afiliado à Epic Games.

## 🙏 Créditos

- [Rare Launcher](https://github.com/RareDevs/Rare) - Projeto original
- [Legendary](https://github.com/derrod/legendary) - Biblioteca Python para Epic Games
