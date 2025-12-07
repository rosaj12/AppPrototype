# AppPrototype - Aplicativo Android MVVM

## 📱 Sobre o Projeto

Este é um protótipo de aplicativo Android construído seguindo a arquitetura **MVVM (Model-View-ViewModel)**, demonstrando as melhores práticas de desenvolvimento Android moderno.

## 🏗️ Arquitetura MVVM

O projeto está organizado seguindo o padrão MVVM:

```
app/src/main/java/com/example/appprototype/
├── data/
│   ├── local/
│   │   ├── AppDatabase.kt      # Configuração do Room Database
│   │   └── UserDao.kt           # Data Access Object
│   ├── model/
│   │   ├── User.kt              # Entidade de dados
│   │   └── Resource.kt          # Wrapper para estados de operação
│   └── repository/
│       └── UserRepository.kt    # Camada de abstração de dados
├── di/
│   └── DatabaseModule.kt        # Módulo de injeção de dependências (Hilt)
├── ui/
│   ├── activity/
│   │   └── MainActivity.kt      # View (Activity)
│   ├── adapter/
│   │   └── UserAdapter.kt       # RecyclerView Adapter
│   └── viewmodel/
│       └── UserViewModel.kt     # ViewModel
└── MyApplication.kt             # Classe Application com Hilt
```

## 🔧 Tecnologias Utilizadas

### Arquitetura e Componentes
- **MVVM Architecture** - Separação de responsabilidades
- **LiveData** - Observação de dados reativa
- **ViewModel** - Gerenciamento de estado da UI
- **Repository Pattern** - Abstração de fontes de dados

### Bibliotecas Principais
- **Room Database** (2.6.1) - Persistência de dados local
- **Hilt** (2.48) - Injeção de dependências
- **Kotlin Coroutines** (1.7.3) - Programação assíncrona
- **ViewBinding & DataBinding** - Binding de views
- **Material Design 3** - Componentes de UI modernos
- **Retrofit** (2.9.0) - Cliente HTTP (preparado para APIs REST)

## 📦 Funcionalidades Implementadas

- ✅ CRUD completo de usuários (Create, Read, Update, Delete)
- ✅ Persistência local com Room Database
- ✅ UI reativa com LiveData e ViewModel
- ✅ Injeção de dependências com Hilt
- ✅ RecyclerView com DiffUtil para performance otimizada
- ✅ Validação de dados de entrada
- ✅ Tratamento de estados (Loading, Success, Error)
- ✅ Material Design 3 UI

## 🚀 Como Executar

### Pré-requisitos
- Android Studio Hedgehog (2023.1.1) ou superior
- JDK 17
- Android SDK com API Level 34
- Gradle 8.1.0+

### Passos para executar

1. Clone o repositório ou abra o projeto no Android Studio

2. Sincronize o projeto com Gradle:
   ```
   File > Sync Project with Gradle Files
   ```

3. Execute o aplicativo:
   - Conecte um dispositivo Android ou inicie um emulador
   - Clique em "Run" (ou pressione Shift+F10)

## 📋 Estrutura das Camadas

### Model (Dados)
- **User.kt**: Entidade de dados com anotações Room
- **Resource.kt**: Classe selada para representar estados de operação

### Repository
- **UserRepository.kt**: Gerencia todas as operações de dados, servindo como fonte única de verdade

### ViewModel
- **UserViewModel.kt**: Expõe dados para a UI via LiveData, contém lógica de negócio

### View
- **MainActivity.kt**: Observa o ViewModel e atualiza a UI, sem lógica de negócio
- **UserAdapter.kt**: Adapter do RecyclerView para exibir lista de usuários

### Dependency Injection
- **DatabaseModule.kt**: Fornece instâncias do banco de dados e DAOs
- **MyApplication.kt**: Classe Application configurada com Hilt

## 🔄 Fluxo de Dados

```
View (Activity) 
    ↓ Ação do usuário
ViewModel 
    ↓ Chamada de método
Repository 
    ↓ Operação de dados
DAO (Room Database)
    ↓ LiveData/Flow
Repository 
    ↓ Resource<Data>
ViewModel (LiveData)
    ↓ Observação
View (UI Update)
```

## 📱 Telas do Aplicativo

### MainActivity
- Formulário para adicionar usuários (Nome, Email, Idade)
- Lista de usuários cadastrados
- Botão de deletar para cada usuário
- Estados visuais (loading, empty, error)

## 🧪 Testes

O projeto está preparado para testes com:
- JUnit 4.13.2 para testes unitários
- Espresso para testes de UI

## 📄 Licença

Este é um projeto de protótipo para fins educacionais e demonstração de arquitetura MVVM.

## 👨‍💻 Desenvolvimento

Desenvolvido seguindo as melhores práticas do Android:
- Clean Architecture
- SOLID Principles
- Separation of Concerns
- Single Source of Truth
- Unidirectional Data Flow

---

**Nota**: Este é um protótipo funcional demonstrando a arquitetura MVVM. Para uso em produção, considere adicionar:
- Testes unitários e de integração
- Gestão de configuração (BuildConfig)
- Logging estruturado
- Analytics
- Crash reporting
- CI/CD pipeline
