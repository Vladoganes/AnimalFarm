# Проект на Java - Животноводческая ферма:


Здесь представлена программа, которая моделирует работу Животноводческой фермы.

Для работы программы требуется ввести начальные данные фермы:

- Начальный денежный капитал фермы.
- Начальное количество молодых, взрослых и старых животных соответственно.
- Также необходимо ввести срок Контракта, который подписывается с биржей о продаже животных.

После того как введены эти данные и нажатия кнопки "Инициализация" их нельзя будет поменять до окончания моделирования!

Далее для работы программы нужно ввести поля Контракта на текущий год (то есть они могут изменяться в течение всего моделирования):

- Количество животных каждого возраста, которые ферма обязуется продать в конце года.
- Цена за 1 животное каждого возраста
- Цена корма для 1 Взрослого животного, которую ферма должна выплатить Бирже, чтобы не произошел падеж скота. Количество корма,
которое необходимо для Молодого и Старого животного находится через корм для 1 Взрослого.
- А также сумму неустойки, которая ферма должна выплатить за каждое непроданное животное согласно текущему контракту.

После того, как введены поля Данных контракта и ферма уже инициализирована, пользователь может нажать кнопку "Выполнить 1 шаг моделирования".
При нажатии этой кнопки ферма отработает 1 год по текущему Контракту с Биржей и выведет статистическую информацию по работе с биржей в таблицу, 
с которой пользователь может ознакомиться. При дальнейшем нажатии на эту кнопку в таблице будет появляться новая соответствующая году моделирования 
колонка до тех пор, пока не закончится Срок контракта (который задавался в начале моделирования) или пока ферма не обанкротится.

При нажатии на кнопку "Выполнить моделирование до конца" ферма сразу отработает все года по Первому заключенному контракту.

После окончания моделирования пользователь может сразу же начать новое моделирование, заполнив все соответствующие поля по Ферме и Контракту и нажав 
кнопки "Инициализация".

Стоит отметить, что есть 2 версии графического интерфейса работающей программы:
- Первый расположен в ветке master и там вся статистическая информация выводится в многострочное поле вывода в Главном окне (на таблицу там внимания обращать
не нужно).
- Второй расположен в ветке secondProgramVersion и там вся статистическая информация выводится в отдельное окно с таблицей, которая динамически заполняется, как
было описано выше.

## Как высчитывается рентабельность?
Рентабельность (в процентах) высчитывается по формуле:
$$Рентабильность  =  {Чистая Прибыль \over Расходы} * 100$$
$$Чистая Прибыль  = Доход С Продажи Животных - Расходы$$
$$Расходы  =  ДеньгиЗаКорм + СуммарнаяНеустойкаПоКонтракту$$



