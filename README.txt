Необходимо реализовать консольное CRUD приложение, которое имеет следующие сущности:

Customer
Specialty
Account
AccountStatus (enum ACTIVE, BANNED, DELETED)
Customer-> Set<Specialty> specialties+ Account account
Account -> AccountStatus

В качестве хранилища данных необходимо использовать JSON файлы:
customers.json, specialties.json, accounts.json

Пользователь в консоли должен иметь возможность создания, получения, редактирования и удаления данных.

Слои:
model - POJO классы
repository - классы, реализующие доступ к текстовым файлам
controller - обработка запросов от пользователя
view - все данные, необходимые для работы с консолью

Например: Customer, CustomerRepository, CustomerController, CustomerView и т.д.


Для репозиторного слоя желательно использовать базовый интерфейс:
interface GenericRepository<T,ID>

interface CustomerRepository extends GenericRepository<Customer, Long>

class JsonCustomerRepositoryImpl implements CustomerRepository

Изменения:
1) Добавил чтение запись данных json вместо txt.
2) Добавил проверку перед запуском программы на наличие необходимых файлов. Если файлы отсутствуют, то программа сама создает их.
3) Реализовал функционал через два шаблона:
Шаблон стратегия. Теперь есть интерфейс Menu и каждый класс с этим имплементированным интерфейсом нужно запускать через MenuSelect
Шаблон фасад. Теперь появилась папка File в ней три класса имеющие интерфейс FileSystem c единственным методом create.
Данные классы помещены в класс StorageInitializer и методом createFiles() создаются сразу три файла из этих классов.
