package com.veygard.android.geoquiz

import androidx.annotation.StringRes

class Question(val text: String, val correctAnswer: String, val answer2: String, val answer3: String, val answer4: String, var questionShowed: Boolean) {

    companion object {
        var questionListForGame : List<Question> = arrayListOf()

        fun getQuestionListAtStart(): List<Question> {
            return listOf(
                Question(" Кто из этих знаменитых людей не является тезкой остальных? ", "Лужков", " Боярский", " Лермонтов", " Горбачев", true),
                Question(" Какой ресторан находится на углу Старого и Нового Арбата? ", "Прага", " Пекин", " Белград", " София ", false),
                Question(" Какой вид березы славится красивой древесиной? ", "Карельская", " Курильская", " Корейская", " Канадская ", false),
                Question(" Какая поэма есть у В. В. Маяковского? ", "\"Флейта-позвоночник\"", " \"Свирель-губы\"", " \"Скрипка-ладони\"", " \"Барабан-нервы\" ", false),
                Question(" Какая из этих рек впадает в Азовское море? ", "Дон", " Днестр", " Южный Буг", " Днепр ", false),
                Question(" Кто из этих великих футболистов прошлого был защитником? ", "Беккенбауэр", " Платини", " Гарринча", " Эйсебио ", false),
                Question(" Какую икру больше всего любил Джеймс Бонд? ", "Белужью", " Осетровую", " Севрюжью", " Стерляжью ", false),
                Question(" Какой цвет волос был у возлюбленной Тристана Изольды, если верить Бедье? ", "Белокурый", " Рыжий", " Каштановый", " Вороново крыло ", false),
                Question(" У автомобилей какой из этих стран международный регистрационный знак DZ? ", "Алжир", " Белиз", " Бенин", " Замбия ", false),
                Question(" Одним из направлений какой религиозной философии является учение дзен? ", "Буддизм", " Иудаизм", " Индуизм", " Даосизм ", false),
                Question(" Что из этого является видом ткани? ", "Креп-жоржет", " Креп-лизет", " Креп-мюзет", " Креп-жаннет ", false),
                Question(" Как называется комедия В. В. Маяковского? ", "Клоп", " Пена", " Жук", " Паук ", false),
                Question(" Кто является чемпионом гонок 'Формулы-1' 1998-99 г.г.? ", "Хаккинен", " М. Шумахер", " Барикелло", " Кулхард ", false),
                Question(" Чье произведение легло в основу оперы Дж. Верди 'Травиата'? ", "А.Дюма-сына", " В. Гюго", " О. Бальзака", " Г. Флобера ", false),
                Question(" Какой из этих городов - родина Казановы? ", "Венеция", " Неаполь", " Милан", " Флоренция ", false),
                Question(" Кто считается основоположником кубизма? ", "П. Пикассо", " В. Кандинский", " Ф. Леже", " К. Малевич ", false),
                Question(" Территория какой из этих стран - наибольшая? ", "Япония", " Германия", " Италия", " Финляндия ", false),
                Question(" Какая из этих кислот является витамином? ", "Никотиновая", " Молочная", " Яблочная", " Янтарная ", false),
                Question(" Какая очередность этих трех букв в русском алфавите правильная? ", "Ъ Ы Ь", " Ь Ы Ъ", " Ъ Ь Ы", " Ь Ъ Ы ", false),
                Question(" Какая березка стояла во поле? ", "Кудрявая", " Высокая", " Зеленая", " Засохшая ", false),
                Question(" В каком городе в 1932 году был проведён первый международный кинофестиваль? ", "Венеция", " Канн", " Париж", " Берлин ", false),
                Question(" С какой фигуры начинаются соревнования по игре в \"Городки\"? ", "Пушка", " Пулемётное гнездо", " Артиллерия", " Часовые ", false),
                Question(" Сколько раз в сутки подзаводят часы на Спасской башне московского Кремля? ", "Два", " Один", " Три", " Четыре ", false),
                Question(" Кто получил первую Нобелевскую премию по литературе? ", "Поэт", " Романист", " Драматург", " Эссеист ", false),
                Question(" С какой буквы начинаются слова, опубликованные в 16 м томе последнего издания Большой Советской энциклопедии? ", "М", " Н", " О", " П ", false),
                Question(" Как назвали первую кимберлитовую трубку, открытую Ларисой Попугаевой 21 августа 1954 года? ", "\"Зарница\"", " \"Удачная\"", " \"Мир\"", " \"Советская\" ", false),
                Question(" В честь какого растения область Фриули-Венеция-Джулия в Италии ежегодно проводит трёхмесячный фестиваль? ", "спаржа", " лук", " фасоль", " артишок ", false),
                Question(" Как \"в народе\" называют химическое соединение - тринитротолуол? ", "Тротил", " Пластид", " Гексаген", " Динамит ", false),
                Question(" У кого из \"вампиров\" кровь пьют только самки? ", "Комары", " Летучие мыши", " Пиявки", " Клещи ", false),
                Question(" В каком российском городе к его 300-летию был открыт памятник Зайцу? ", "Санкт-Петербург", " Москва", " Воронеж", " Тула ", false),
                Question(" Для чего зайцу его большие уши? ", "Они как рупоры, зайцу слышны даже самые тихие шорохи", " Для красоты", " Обмахиваться в жару", " Для устрашения врагов-хищников ", false),
                Question(" Что обычно охотники замечают за зайцем? ", "Он барабанит", " Он трубит", " Он бренчит", " Он напевает ", false),
                Question(" Что при травле зайца кричали охотники? ", "\"Ату!\"", " \"Агу!\"", " \"А-та-та!\"", " \"Ура!\" ", false),
                Question(" Какого зайца НЕ бывает? ", "Чувак", " Тумак", " Русак", " Беляк ", false),
                Question(" Кто такой агути? ", "Горбатый заяц", " Сутулый заяц", " Лысый заяц", " Хромой заяц ", false),
                Question(" Заячьей картошкой в народе называют: ", "Гриб дождевик", " Морковь", " Капусту", " Кору деревьев ", false),
                Question(" В русских сказках лисичка-сестричка, коза-дереза, а зайчик-... Кто? ", "Побегайчик", " Попугайчик", " Петляйчик", " Обгрызайчик ", false),
                Question(" Какая российская автономия полностью окружена Краснодарским краем? ", "Адыгея", " Хакасия", " Ингушетия", " Удмуртия ", false),
                Question(" Где жил старик со своею старухой из сказки А.С. Пушкина \"Сказка о рыбаке и рыбке\"? ", "в землянке", " в избушке", " в лачужке", " в шалаше ", false),
                Question(" Какой рыболовной снастью ловил рыбу старик из сказки А.С. Пушкина \"Сказка о рыбаке и рыбке\"? ", "Невод", " Удочкой", " Бреднем", " Сачком ", false),
                Question(" Маленький мук из сказки был...? ", "Карликом", " Ребенком", " Лиллипутом", " Подростком ", false),
                Question(" Чем промышлял Дуремар из сказки \"Золотой ключик\"? ", "Пиявками", " Лягушками", " Бабочками", " Стрекозами ", false),
                Question(" Где сидит Доктор Айболит? ", "Под деревом", " В Ленинграде", " В домике", " В больнице ", false),
                Question(" Что такое обувница? ", "Предмет мебели для обуви", " Воровка", " Ложка для обуви", " Работница обувной ", false),
                Question(" Корюшка - это... ", "Рыбка", " Птичка", " Крючок", " Растение ", false),
                Question(" В какое море впадает Жёлтая река? ", "Жёлтое море", " Ионическое море", " Чёрное море", " Каспийское море ", false),
                Question(" Кто был главным врагом трех поросят? ", "Волк", " Троллль", " Мышь", " Мясник ", false),
                Question(" Какая планета совершает 1 оборот вокруг солнца со скоростью 47,9 км/с ", "Меркурий", " Марс", " Юпитер", " Сатурн ", false),
                Question(" Какого города нет в Казахстане? ", "Самарканд", " Шымкент", " Караганда", " Пвлодар ", false),
                Question(" Кем по образованию был Антон Павлович Чехов? ", "Врач", " Юрист", " Учитель", " Дипломат ", false),
                Question(" Какой из театров Москвы носит имя А.П. Чехова? ", "МХАТ (МХТ)", " Большой театр", " Малый театр", " Театр \"Сатирикон\" ", false),
                Question(" Как звали тонкого в рассказе А.П. Чехова \"Толстый и тонкий\"? ", "Порфирий", " Акакий", " Нафанаил", " Ермолай ", false),
                Question(" Кому из обитателей палаты №6 в одноименном рассказе А.П.Чехова разрешалось выходить из флигеля и больничного двора на улицу? ", "Моисейке", " Громову", " Сортировщику", " Паралитику ", false),
                Question(" Сестры с каким именем не было среди героинь чеховских \"Трёх сестёр\"? ", "Анна", " Мария", " Ирина", " Ольга ", false),
                Question(" Какой из этих литературных шедевров с четвероногими героями принадлежит перу А.П. Чехова? ", "\"Каштанка\"", " \"Белый пудель\"", " \"Му-му\"", " \"Белый Бим Чёрное Ухо\" ", false),
                Question(" Рассказ А.П.Чехова о несчастном ревматике. ", "Сапоги", " Случай из практики", " Хирургия", " Припадок ", false),
                Question(" В какой пьесе А.П. Чехова герой обращался к шкафу с эпитетом \"многоуважаемый\"? ", "\"Вишневый сад\"", " \"Три сестры\"", " \"Чайка\"", " \"Дядя Ваня\" ", false),
                Question(" В каком итальянском городе была выведена известная порода комнатных собак? ", "Болонья", " Сиракуза", " Падуя ", "Рим", false),
                Question(" Какая рыба является самым крупным пресноводным хищником? ", "Cом", " Судак", " Щука ", "Лещь", false),
                Question(" Президент какой республики в 1996 году был избран президентом ФИДЕ? ", "Калмыкии", " Абхазии", " Башкирии ", "Тува", false),
                Question(" Изобретатель тачки? ", "Паскаль", " Пифагор", " Архимед", " Эдисон ", false),
                Question(" Назовите греческого атлета, выигравшего марафон на Первой Олимпиаде нашего времени. ", "Луис", " Костакис", " Клиридис", " Лумидис ", false),
                Question(" Кого из российских правителей называли \"русским Гамлетом\" ", "Павел", " Петр ", "Александр", "Алексей", false),
                Question(" Что из перечисленного является единственным колониальным владением Британии в Европе? ", "Гибралтар", " Монако", " Андорра", " Люксембург ", false),
                Question(" Облик какой женщины дошёл до наших дней благодаря скульптору Тутмесу? ", "Нефертити", " Клеопатры", " Далилы", " Семирамды ", false),
                Question(" Кто из этих известных актеров и режиссеров, народных артистов СССР, стал министром культуры своей страны? ", "Богдан Ступка", " Донатас Банионис", " Вия Артмане", " Роберт Стуруа ", false),
                Question(" Кто из этих людей не был писателем ? A: Во B: Ге C: Ле D: По ", "Ге", " Во", " По", " Ле ", false),
                Question(" Кто стал самым молодым маршалом Советского Союза в истории СССР? ", "М.Н.Тухачевский", " Г.К.Жуков", " В.К.Блюхер", " К.К.Рокоссовскийий ", false),
                Question(" Какой из этих городов до воссоединения Германии был в составе ГДР?", "Веймар", " Ганновер", " Трир", " Ганновер ", false),
                Question(" Как назывался первый атомный ледокол СССР? ", "Ленин", " Сталин", " Свердлов", " Киров ", false),
                Question(" В какой стране на население наводили ужас тонтон-макуты? ", "Гаити", " Куба", " Парагвай", " Гватемала ", false),
                Question(" Как называлась столица государства ацтеков? ", "Теночтитлан", " Монтесума", " Кетцалькоатль", " Мехико ", false),
                Question(" Назовите административный центр (столицу) штата Флорида ", "Таллахасси", " Майами", " Джуно", " Сакраменто ", false),
                Question(" Какое российское флотское звание было у Витуса Беринга? ", "Капитан-командор", " Контр-адмирал", " Вице-адмирал", " Капитан 1 ранга ", false),
                Question(" Что означает количество пятнышек у божьей коровки? ", "Вид", " Возраст", " Пол", " Дату рождения ", false),
                Question(" Какая пара этих знаменитых писателей состоит из родных братьев? ", "Генрих и Томас Манны", " Ирвин и Бернард Шоу", " Ян и Пабло Неруды", " Грэм и Александр Грины ", false),
                Question(" Кому и в какой глаз впился князь Гвидон в образе комара? ", "Поварихе в правый", " Ткачихе в левый", " Поварихе в левый", " Ткачихе в правый ", false),
                Question(" Игроков какой команды называют \"армейцами\" ", "\"ЦСКА\"", " \"Динамо\"", "\" Спартак\"", "\" Локомотив\" ", false),
                Question(" К какому семейству рыб принадлежит пескарь? ", "Карповые", " Щуковые", " Бычковые", " Колюшковые ", false),
                Question(" Кто из этих астрономов 1 ноября 1977 года открыл Хирон - малую планету Солнечной системы? ", "Чарльз Коуэл", " Дирк Брауэр", " Карл Сейферт", " Жан Пикар ", false),
                Question(" Кто из этих русских писателей прожил самую долгую жизнь? ", "И. А. Бунин", " Л. Н. Толстой", " М. М. Пришвин", " П. П. Бажов ", false),
                Question(" При введении в России табели о рангах право на личное дворянство имели даже чиновники низшего, 14-го класса. А с какого класса это право сохранилось после 1845 г.? ", "9", " 8", "10", "11", false),
                Question(" Какой советский орден был инкрустирован бриллиантами? ", "Орден Победы", " Орден Ленина", " Орден Красного Знамени", " Орден Красной Звезды ", false),
                Question(" Под каким названием всему миру была известна разведслужба бывшей ГДР? ", "ШТАЗИ", " МИ-6", " АНБ", " МОССАД ", false),
                Question(" Сказочный Доктор? ", "Айболит", " Окулист", " Фельдшер", " Медик ", false),
                Question(" Куда отправляет Военкомат? ", "Служить", " Отбывать", " Отдыхать", " Работать ", false),
                Question(" Сколько циферблатов у курантов Спасской башни? ", "4", " 2", "6", "8", false),
                Question(" В каком году советские войска одержали победу в Курской битве? ", "1943", " 1939", "1942", "1944", false),
                Question(" Кто изображен на правом плече Марадоны? ", "Че Гевара", " Фидель Кастро", " Ким Чен Ир", " Пеле ", false),
                Question(" В какой из этих столиц союзных республик раньше всего появилось метро? ", "Тбилиси", " Ереван", " Баку", " Минск ", false),
                Question(" В форме какого животного обычно делают копилки для монет? ", "Свинья", " Корова", " Собака", "Кошка", false),
                Question(" На окошке сидит кошка всё как у кошки но не кошка. ", "кот", " тигр", " котёнок", " дикая кошка ", false),
                Question(" В каком образе бог Вишну не появлялся на земле? ", "Слона", " Черепахи", " Рыбы", " Вепря ", false),
                Question(" Как заканчивается выражение \"Врет как...\"? ", "Сивый мерин", " Зеленый змий", " Розовый слон", " Серый заяц ", false),
                Question(" Какое устройство применяли во время инквизиции? ", "испанский сапог", " португальский башмак", " японские гета", " русский валенок ", false),
                Question(" Какой из этих регионов - не на территории Италии? ", "Савойя", " Тоскана", " Ломбардия", " Умбрия ", false),
                Question(" Как по-научному называется питьевой спирт? ", "Этанол", " Метанол", " Политура", " Микстура ", false),
                Question(" Какая из этих премий впоследствии была переименована в Государственную премию СССР? ", "Сталинская", " Ломоносовская", " Ленинская", " Нобелевская ", false),
                Question(" В какой области науки проявились выдающиеся способности Софьи Ковалевской? ", "Математика", " Химия", " Биология", " Философия ", false),
                Question(" Какую из этих опер написал Моцарт? ", "\"Свадьба Фигаро\"", " \"Сицилийская вечерня\"", " \"Сельская честь\"", " \"Севильский цирюльник\" ", false),
                Question(" Какое из этих прозаических произведений, А.С.Пушкин написал от женского имени? ", "\"Рославлев\"", " \"Выстрел\"", " \"Кирджали\"", " \"Египетские ночи\" ", false),
                Question(" Из какого меxа сделаны шапки королевскиx гвардейцев Великобритании? ", "Медвежий", " Волчий", " Овечий", " Соболиный ", false),
                Question(" Назовите вслед за Н. В. Гоголем вторую беду России, кроме дорог ? ", "Дураки", " Умники", " Пьяницы", " Олигархи ", false),
                Question(" Как иначе называют гевею? ", "резиновое дерево", " виниловое дерево", " нейлоновое дерево", " пластмассовое дерево ", false),
                Question(" Что у моряков называется камбузом? ", "Кухня", " Машинное отделение", " Мостик капитана", " Кладовая ", false),
                Question(" Как звали знаменитого мореплавателя Кука? ", "Джеймс", " Томас", " Джон", " Джозеф ", false),
                Question(" Как звали прославленного английского флотоводца Нельсона? ", "Горацио", " Грегори", " Герберт", " Георг ", false),
                Question(" Чей бюст созерцает Аристотель на портрете Рембрандта? ", "Гомера", " Сократа", " Аякса", " Геракла ", false),
                Question(" Какие цветы распускаются в парке \"Чаир\"? ", "Розы", " Астры", " Хризантемы", " Ромашки ", false),
                Question(" Из какого злака получают манную крупу? ", "пшеница", " рожь", " ячмень", " просо ", false),
                Question(" Какая террористическая организация похитила и убила премьер-министра Италии Альдо Моро? ", "Красные бригады", " Коза ностра", " Черный сентябрь", " Черные пантеры ", false),
                Question(" Футболист какого амплуа изобрёл \"удар скорпиона\", отбив мяч в прыжке пятками? ", "вратарь", " полузащитник", " защитник", " нападающий ", false),
                Question(" Как называется группа из пяти фигурок - официальный талисман Олимпийских игр в Пекине? ", "\"Дети удачи\"", " \"Сыновья победы\"", " \"Братья фортуны\"", " \"Друзья спорта\" ", false),
                Question(" Главный герой какого романа Ф.М. Достоевского мечтал стать Ротшильдом? ", "Подросток", " Идиот", " Бесы", " Братья Карамазовы ", false),
                Question(" Кто из этих великих российских архитекторов был ярким представителем стиля барокко? ", "Растрелли", " Монферран", " Баженов", " Казаков ", false),
                Question(" Какой из этих химических элементов был открыт в России? ", "Рутений", " Родий", " Тулий", " Самарий ", false),
                Question(" Как называется надпись на лицевой или оборотной стороне монеты, медали? ", "Легенда", " Гурт", " Поясниловка", " Номинал ", false),
                Question(" Какое кодовое название было у первого термоядерного взрывного устройства? ", "Майк", " Тринити", " Малыш", " Толстяк ", false),
                Question(" Какой пистолет Остап Бендер обещал дать Кислярскому в романе \"Двенадцать стульев\"? ", "парабеллум", " наган", " маузер", " браунинг ", false),
                Question(" Кто написал текст российского гимна \"Боже, царя храни\"? ", "Василий Жуковский", " Гаврила Державин", " Пётр Вяземский", " Фёдор Тютчев ", false),
                Question(" Кто добился упразднения ордена тамплиеров в 1312 году? ", "Филлип IV", " Филипп II Август", " Филипп II Габсбург", " Филлип III ", false),
                Question(" Какой чин имел Лукаш, у которого бравый солдат Швейк был денщиком? ", "Поручик", " Подпоручик", " Капитан", " Фельдкурат ", false),
                Question(" Какая из этих команд собаке, означает \"смирно\"? ", "Тубо!", " Куш!", " Апорт!", " Фас! ", false),
                Question(" Сколько фишек надо поставить подряд в линию, чтобы одержать победу в рэндзю? ", "5", " 6", "7", "8", false),
                Question(" Какой крепёжный не имеет резьбы? ", "шплинт", " болт", " шуруп", " гайка ", false),
                Question(" На чём рисуют специалисты по латте-арт? ", "На кофейной пенке", " На кофейной этикетке", " На кофейной чашке", " На кофейном дереве ", false),
                Question(" Кому в пожизненное пользование была предоставлена скрипка работы Антонио Страдивари? ", "В. Спивакову", " Е. Мравинскому", " М. Ростроповичу", " И. Брилю ", false),
                Question(" Что несколько лет назад обнаружил в Мёртвом море израильский учёный Соломон Вассер? ", "грибы", " кораллы", " жемчуг", " осколок метеорита ", false),
                Question(" Какой стране принадлежат Шантарские острова? ", "Россия", " Индонезия", " Филиппины", " Въетнам ", false),
                Question(" Назовите имя и отчество морганатического супруга императрицы Елизаветы Петровны графа Разумовского ", "Алексей Григорьевич", " Алексей Кириллович", " Андрей Кириллович", " Кирилл Григорьевич ", false),
                Question(" \"Заморская провинция\" какой страны до самого последнего времени существовала на территории Китая? ", "Португалия", " Испания", " Нидерланды", " Франция ", false),
                Question(" Героя романа какого из этих писателей звали Ловеласом? ", "С. Ричардсон", " Т. Готье", " А. де Мюссе", " Ж. Санд ", false),
                Question(" Какая олимпийская дистанция плавания самая длинная? ", "1500 м", " 800 м", " 2000 м", " 1000 м ", false),
                Question(" Какое животное изображено на гербе Челябинска ", "Верблюд", " Лиса", " Волк", " Медведь ", false),
                Question(" Какая из этих археологических культур относится к мезолиту? ", "Азильская", " Хассунская", " Халафская", " Перигорская ", false),
                Question(" Кто из великих художников Возрождения расписывал Сикстинскую капеллу Ватикана? ", "Микеланджело", " Леонардо", " Рафаэль", " Тициан ", false),
                Question(" Из скольких слогов традиционно состоит хайку? ", "17", " 7", "21", "100", false),
                Question(" Денежная единица Узбекистана ", "Сум", " Сомони", " Тугрики", " Тенге ", false),
                Question(" Валюта Таджикистана ", "Сомони", "Тугрики", "Сум", "Тенге", false),
                Question(" Как назывался первый пассажирский пароход, пересекший Атлантический океан без помощи парусов? ", "Сириус", " Вега", " Альдебаран", " Альтаир ", false),
                Question(" Как называется русский традиционный пирог? ", "Расстегай", " Выпори", " Отлупи", " Забей ", false),
                Question(" История какого театра началась со спектакля \"Добрый человек из Сезуана\"? ", "театр на Таганке", " Ленком", " \"Современник\"", " \"Табакерка\" ", false),
                Question(" Назовите населенный пункт, куда Петр II сослал А. Д. Меньшикова ", "Березов", " Тобольск", " Торжок", " Кемь ", false),
                Question(" Какое произведение Моцарта было завершено другим композитором? ", "Реквием", " \"Дон Жуан\"", " \"Волшебная флейта\"", " \"Свадьба Фигаро\" ", false),
                Question(" Их в России 19-го века, называли рыцарями зеленых полей ", "Шулеры", " Садовники", " Егеря", "Гвардейцы ", false),
                Question(" Кто из этих должностных лиц, заведовал при дворе Екатерины II винными погребами? ", "Обер-Шенк", " Обер-Гофмаршал", " Обер-Гофмейстер", " Обер-шталмейстер ", false),
                Question(" Какой актёр поменял своё настоящее имя Альберт на Олег? ", "Олег Борисов", " Олег Анофриев", " Олег Даль", " Олег Анофриев ", false),
                Question(" В какой стране зародилась игра кегли? ", "Германия", " Голландия", " Швейцария", " Австрия ", false),
                Question(" Что из этих сооружений стало названием романа М. С. Шагинян? ", "Гидроцентраль", " Теплоцентраль", " Газоцентраль", " Электроцентраль ", false),
                Question(" Кто сыграл Мальволио в фильме \"Двенадцатая ночь\"? ", "Меркурьев", " Ильинский", " Филиппов", " Яншин ", false),
                Question(" Какова масса ядра в соревнованиях женщин? ", "4 кг", "3 кг", "3,5 кг", "4,5 кг", false),
                Question(" Какое вино необходимо для приготовления классического крюшона? ", "Белое столовое", " Мадера", " Портвейн", " Красное столовое ", false),
                Question(" Какому времени года соответствует 2-й месяц французского республиканского календаря брюмер? ", "Осень", " Весна", " Лето", " Зима ", false),
                Question(" С каким животным связана история основания города Ярославля? ", "Медведь", " Скунс", " Мышь", " Белка ", false),
                Question(" Какие московские пруды одно время назывались Пионерскими? ", "Патриаршие", " Борисовкие", " Сарлетские", " Чистые ", false),
                Question(" Сборная какой страны стала в 1991 году первым чемпионом мира по женскому футболу? ", "США", " Бразилии", " России", " Польши ", false),
                Question(" Какую оперу поставил в Мариинском театре Андрон Кончаловский? ", "\"Война и мир\"", " \"Борис Годунов\"", " \"Хованщина\"", " \"Евгений Онегин\" ", false),
                Question(" Какой математический символ ввёл в математику Лейбниц? ", "интеграл", " логарифм", " модуль", " квадратный корень ", false),
                Question(" Сколько стран одновременно победили на конкурсе \"Евровидение\" в 1969 году? ", "Четыре", " Две", " Три", " Пять ", false),
                Question(" Кто такой речной сверчок? ", "птица", " рыба", " насекомое", " рептилия ", false),
                Question(" Назовите фамилию режиссера фильма \"9 дней одного года\". ", "Ромм", " Роом", " Калатозов", " Баталов ", false),
                Question(" Какое прозвище, ставшее фамилией, получил знаменитый шведский хоккеист Свен Юханссон? ", "Тумба", " Комод", " Сейф", " Шкаф ", false),
                Question(" Кто из этих художников написал знаменитый портрет Ф.И. Шаляпина в шубе? ", "Кустодиев", " Бродский", " Репин", " Врубель ", false),
                Question(" В какиx войскаx служил Лев Толстой? ", "Артиллерия", " Флот", " Пеxота", " Кавалерия ", false),
                Question(" На какой реке стоит Тверь? ", "Волга", " Днепр", " Урал", " Тверь ", false),
                Question(" Как по-другому называют койота? ", "Луговой волк", " Пустынный волк", " Тропический волк", " Лесной волк ", false),
                Question(" В каком клубе завершил свою карьеру легендарный хоккеист Уэйн Гретцки? ", "\"Нью-Йорк Рейнджерс\"", " \"Монреаль Канадиенс\"", " \"Филодельфия Флаерс", " \"Уолл-стрит Всет\" ", false),
                Question(" Какая книга в русском переводе впервые вышла под названием \"Не любо, не слушай, а врать не мешай\"? ", "\"Приключения Мюнхгаузена\"", " \"Гаргантюа и Пантагрюэль\"", " \"Путешествие Гулливера\"", " \"Робинзон Крузо\" ", false),
                Question(" Какую рыбу совершенно справедливо можно назвать макрелью? ", "скумбрию", " ставриду", " сёмгу", " тунца ", false),
                Question(" Какой из русских живописцев взял в качестве псевдонима своё детское прозвище? ", "В.Г.Перов", " И.Е.Репин", " В.И.Суриков", " А.К.Саврасов ", false),
                Question(" Как называется яркая звезда - альфа Южной Рыбы? ", "Фомальгаут", " Ригель", " Ахернар", " Бетельгейзе ", false),
                Question(" Кто основал 1250 лет назад во франкском государстве династию Каролингов? ", "Пипин Короткий", " Карл Великий", " Хлодвиг I", " Карл Мартелл ", false),
                Question(" Кого иногда называют спрутом? ", "осьминога", " кашалота", " медузу", " каракатицу ", false),
                Question(" Какой язык является государственным языком Эфиопии? ", "Амхарский", " Тигре", " Арабский", " Суахили ", false),
                Question(" В честь кого получил своё имя популярный салат \"Цезарь\"? ", "повара — создателя салата", " птицы цесарки", " Чезаре Борджиа", " Юлия Цезаря ", false),
                Question(" Отдел пищеварительной системы, на стенкаx которого располагаются ворсинки? ", "Тонкая кишка", " Пищевод", " Прямая кишка", " Желудок ", false),
                Question(" Температура абсолютного нуля по Фаренгейту - это: ", "-457.87 градусов", " -273.15 граду", " -17.8 градусов", " 0 градусов ", false),
                Question(" Под каким именем известен любителям советской поэзии Евгений Скурко? ", "Максим Танк", " Фёдор Пушка", " Семён Пулемёт", " Егор Бронепоезд ", false),
                Question(" Как называется третья нота в гамме в соль мажор? ", "Си", " Фа", " Ре", " Ля ", false),
                Question(" Какая порода собак является миниатюрной копией грейхаунда? ", "Левретка", " Такса", " Болонка", " Чау-чау ", false),
                Question(" Что означает слово \"Баунти\"? ", "Щедрость", " Сладость", " Кокос", " Жадность ", false),
                Question(" В каком веке Ватикан перестал издавать Индекс запрещённых книг? ", "20", " 17", "18", "19", false),
                Question(" В какой день ноября проводятся каждые четыре года выборы президента США? ", "В 1-й Вт после 1-го Пн", " В любой по решению Сената", " В 1-е воскресенье", " За 20 дн. до дня Благодарения ", false),
                Question(" Что в свободное время мастерил химик Д.И.Менделеев? ", "Чемоданы", " Коврики", " Табуретки", " Игрушки ", false),
                Question(" Какое пиротехническое изделие получило название в честь впервые примененного в Индии средства сигнализации? ", "бенгальский огонь", " петарда", " фейерверк", " салют ", false),
                Question(" Какой российский политик родился в селе Бутка Свердловской области? ", "Борис Ельцин", " Михаил Горбачёв", " Сергей Шойгу", " Владимир Путин ", false),
                Question(" Кому приходилось заниматься разделыванием под орех, что и породило известный неологизм? ", "столяру", " повару", " конюху", " кузнецу ", false),
                Question(" Какой народный промысел прославил тобольских мастеров? ", "резьба по кости", " чугунное литьё", " малахитовые шкатулки", " пуховые платки ", false),
                Question(" Кто, по русским летописям, заселил европейскую часть нынешней России после потопа? ", "Иафет", " Хам", " Ной", " Сим ", false),
                Question(" Какой жанр голандской живописи 17 века предназначался для напоминания о быстротечности жизни и неизбежности смерти? ", "Ванитас", " Готика", " Бодегон", " Бомбочады ", false),
                Question(" Как по-научному называется выгорание взрывчатого вещества? ", "Дефлаграция", " Диффамация", " Эксплорация", " Дефламмизация ", false),
                Question(" Что хакеры называют \"пагой\"? ", "Web-страничку", " Погоду", " Игру", " Пейджер ", false),
                Question(" Какой римский император был отцом императора Тита? ", "Веспасиан", " Адриан", " Домициан", " Клавдий ", false),
                Question(" Как раньше называлась игра в шахматы? ", "Чатуранга", " Чачуранга", " Матуранга", " Пануранга ", false),
                Question(" В какой пустыне в 1923 году были впервые обнаружены остатки гнезд динозавров с лежащими там окаменевшими яйцами? ", "Гоби", " Ливийская", " Калахари", " Атакама ", false),
                Question(" Что такое ферменты, биологические катализаторы? ", "Белки", " Кислоты", " Жиры", " Паразиты ", false),
                Question(" На какой реке стоит город Прага? ", "Влтава", " Тиса", " Лаба", " Одра ", false),
                )
        }
    }
}