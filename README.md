# Проект java/ Лабораторная работа №5. Выполнил Денисов Алексей ИТ-13

## Описание проекта
Проект представляет собой реализацию 8 задач по работе с классами, интерфейсами, коллекциями и файлами в Java. Все задачи реализованы в одном проекте с дружественным интерфейсом и проверкой входных данных.

## Структура проекта
### Задание 1: Дробь с кэшированием
```java
class Fraction implements Fractionable {
    private int numerator;
    private int denominator;
    private Double cachedDouble = null;

    public Fraction(int numerator, int denominator) {
        if (denominator == 0) throw new IllegalArgumentException("Знаменатель не может быть 0.");
        if (denominator < 0) {
            denominator = -denominator;
            numerator = -numerator;
        }
        this.numerator = numerator;
        this.denominator = denominator;
    }

    @Override
    public double getDouble() {
        if (cachedDouble == null) {
            cachedDouble = (double) numerator / (double) denominator;
        }
        return cachedDouble;
    }

    @Override
    public void setNumerator(int n) {
        if (this.numerator != n) {
            this.numerator = n;
            this.cachedDouble = null;
        }
    }

    @Override
    public void setDenominator(int d) {
        if (d == 0) throw new IllegalArgumentException("Знаменатель не может быть 0.");
        int newNum = this.numerator;
        int newDen = d;
        if (d < 0) {
            newDen = -d;
            newNum = -newNum;
        }
        if (this.denominator != newDen || this.numerator != newNum) {
            this.denominator = newDen;
            this.numerator = newNum;
            this.cachedDouble = null;
        }
    }
}
```
**Описание:** Класс Fraction реализует интерфейс Fractionable с кэшированием вещественного значения. При изменении числителя или знаменателя кэш инвалидируется.

**Вход:**
```java
Fraction f = new Fraction(1, 2);
f.setNumerator(3);
f.setDenominator(-4);
Fraction f2 = new Fraction(-3, 4);
```
**Вывод:**
```
Создали дробь f = 1/2
Вещественное значение f.getDouble() = 0.5
После setNumerator(3): f = 3/2, getDouble() = 1.5
После setDenominator(-4): f = -3/4, getDouble() = -0.75
Другой объект f2 = -3/4
f.equals(f2)? true
```

### Задание 2: Кот и подсчет мяуканий
```java
class Cat implements Meowable {
    private final String name;
    private int meowCount = 0;

    public Cat(String name) {
        if (name == null || name.trim().isEmpty())
            throw new IllegalArgumentException("Имя кота не может быть пустым.");
        this.name = name.trim();
    }

    @Override
    public void meow() {
        meowCount++;
        System.out.println(name + ": мяу!");
    }

    public int getMeowCount() {
        return meowCount;
    }
}

class Funs {
    public static void meowsCare(Meowable m) {
        for (int i = 0; i < 5; i++) {
            m.meow();
        }
    }
}
```
**Описание:** Класс Cat реализует интерфейс Meowable и подсчитывает количество мяуканий. Метод meowsCare вызывает мяуканье 5 раз.

**Вход:**
```java
Cat cat = new Cat("Барсик");
Funs.meowsCare(cat);
```
**Вывод:**
```
кот: Барсик
Барсик: мяу!
Барсик: мяу!
Барсик: мяу!
Барсик: мяу!
Барсик: мяу!
Кот мяукал: 5 раз(а).
```

### Задание 3: Симметрическая разность списков
```java
class CollectionsUtil {
    public static <T> List<T> symmetricDifference(Collection<T> a, Collection<T> b) {
        Set<T> sa = new HashSet<>(a);
        Set<T> sb = new HashSet<>(b);
        Set<T> union = new HashSet<>(sa);
        union.addAll(sb);
        Set<T> inter = new HashSet<>(sa);
        inter.retainAll(sb);
        union.removeAll(inter);
        return new ArrayList<>(union);
    }
}
```
**Описание:** Метод symmetricDifference возвращает элементы, которые есть только в одном из списков (симметрическая разность).

**Вход:**
```java
List<Integer> L1 = Arrays.asList(1, 2, 3, 4, 4);
List<Integer> L2 = Arrays.asList(3, 4, 5, 6);
```
**Вывод:**
```
L1 = [1, 2, 3, 4, 4]
L2 = [3, 4, 5, 6]
Symmetric difference (включая элементы один раз): [1, 2, 5, 6]
```

### Задание 4: Обработка абитуриентов
```java
class ApplicantsProcessor {
    public static void processApplicantsFile(Path path) throws IOException {
        // Чтение файла и фильтрация абитуриентов
        applicants.stream()
            .filter(a -> a.score1 < 30 || a.score2 < 30)
            .forEach(a -> System.out.println(a.surname + " " + a.name));
    }
}
```
**Описание:** Программа читает файл с данными абитуриентов и выводит тех, кто не набрал минимум 30 баллов по любому предмету.

**Вход (applicants.txt):**
```
3
Ветров Роман 68 59
Анисимова Екатерина 64 88
Петров Иван 25 45
```
**Вывод:**
```
Абитуриенты, не допущенные к сдаче в первом потоке (балл < 30 по любому предмету):
Петров Иван
```

### Задание 5: Звонкие согласные в русском тексте
```java
class RussianConsonantsProcessor {
    private static final Set<Character> VOICED = Set.of(
        'б','в','г','д','ж','з','л','м','н','р'
    );

    public static void processFile(Path path) throws IOException {
        // Обработка текста и поиск звонких согласных
    }
}
```
**Описание:** Программа находит все звонкие согласные буквы, которые встречаются более чем в одном слове текста.

**Вход (russian.txt):**
```
Привет мир, это тестовый текст
для проверки работы программы
```
**Вывод:**
```
Звонкие согласные (алфавитно), которые входят более чем в одно слово: б в д л м н р т 
```

### Задание 6: Печать очереди в обратном порядке
```java
class QueuePrinter {
    public static <T> void printReverse(Queue<T> queue) {
        Deque<T> stack = new ArrayDeque<>();
        for (T elem : queue) stack.push(elem);
        while (!stack.isEmpty()) {
            System.out.print(stack.pop() + " ");
        }
    }
}
```
**Описание:** Метод выводит элементы очереди в обратном порядке с использованием стека.

**Вход:**
```java
Deque<String> queue = new ArrayDeque<>();
queue.add("первый");
queue.add("второй");
queue.add("третий");
```
**Вывод:**
```
Исходная очередь: [первый, второй, третий]
Элементы в обратном порядке: [третий, второй, первый]
```

### Задание 7: Обработка точек и создание ломаной
```java
class PointProcessor {
    public static Polyline buildPolyline(List<Point> points) {
        List<Point> result = points.stream()
            .filter(Objects::nonNull)
            .distinct()
            .sorted(Comparator.comparingDouble(Point::getX))
            .map(p -> p.getY() < 0 ? p.withY(-p.getY()) : p)
            .collect(Collectors.toList());
        return new Polyline(result);
    }
}
```
**Описание:** Стрим обрабатывает список точек: удаляет дубликаты, сортирует по X, делает Y положительным и создает ломаную линию.

**Вход:**
```java
List<Point> points = Arrays.asList(
    new Point(1, -2),
    new Point(2, 3),
    new Point(1, -2),
    new Point(0, -1),
    new Point(3, -4)
);
```
**Вывод:**
```
Исходные точки: [{1.0;-2.0}, {2.0;3.0}, {1.0;-2.0}, {0.0;-1.0}, {3.0;-4.0}]
Результирующая ломаная: Линия [{0.0;1.0}, {1.0;2.0}, {2.0;3.0}, {3.0;4.0}]
```

### Задание 8: Группировка людей по номеру
```java
class PeopleGrouper {
    public static void processPeopleFile(Path path) throws IOException {
        Map<Integer, List<String>> grouped = new TreeMap<>();
        // Чтение файла и группировка по номерам
    }
}
```
**Описание:** Программа читает файл с именами и номерами, нормализует имена и группирует их по номерам.

**Вход (people.txt):**
```
Вася:5
Петя:3
Аня:5
Маша:
Коля:3
```
**Вывод:**
```
Группировка по номеру (номер: [имена]):
{3=[Петя, Коля], 5=[Вася, Аня]}
```

## Заключение
Проект демонстрирует различные аспекты программирования на Java: работу с классами и интерфейсами, обработку коллекций, чтение файлов, использование стримов. Все задачи реализованы с проверкой входных данных и дружественным интерфейсом. Особое внимание уделено правильной обработке исключительных ситуаций и эффективному использованию возможностей языка.
```
