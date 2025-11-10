import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Рабочая директория - " + System.getProperty("user.dir"));
        System.out.println();

        System.out.println("Задача 1 Дробь");
        Fraction f = new Fraction(1, 2);
        System.out.println("Создали дробь f = " + f);
        System.out.println("Вещественное значение f.getDouble() = " + f.getDouble());
        f.setNumerator(3);
        System.out.println("После setNumerator(3): f = " + f + ", getDouble() = " + f.getDouble());
        try {
            f.setDenominator(-4); // знаменатель отрицательный знак переносится в числитель
            System.out.println("После setDenominator(-4): f = " + f + ", getDouble() = " + f.getDouble());
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка при установке знаменателя: " + e.getMessage());
        }
        Fraction f2 = new Fraction( -3, 4 );
        System.out.println("Другой объект f2 = " + f2);
        System.out.println("f.equals(f2)? " + f.equals(f2));
        System.out.println();

        System.out.println("Задача 2 Кот и подсчет мяуканий");
        Cat cat = new Cat("Барсик");
        System.out.println(cat);
        Funs.meowsCare(cat); // метод вызовет meow() несколько раз
        System.out.println("Кот мяукал: " + cat.getMeowCount() + " раз(а).");
        System.out.println();

        System.out.println("Задача 3 Симметрическая разность списков");
        List<Integer> L1 = Arrays.asList(1, 2, 3, 4, 4);
        List<Integer> L2 = Arrays.asList(3, 4, 5, 6);
        List<Integer> sym = CollectionsUtil.symmetricDifference(L1, L2);
        System.out.println("L1 = " + L1);
        System.out.println("L2 = " + L2);
        System.out.println("Сим разность (включая элементы один раз): " + sym);
        System.out.println();

        System.out.println("Задача 4 Абитуриенты из файла applicants.txt");
        Path applicantsPath = Paths.get("applicants.txt");
        if (Files.exists(applicantsPath) && Files.isReadable(applicantsPath)) {
            try {
                ApplicantsProcessor.processApplicantsFile(applicantsPath);
            } catch (IOException e) {
                System.err.println("Ошибка при чтении applicants.txt: " + e.getMessage());
            }
        } else {
            System.out.println("Файл applicants.txt не найден или недоступен — пропускаем задачу 4");
        }
        System.out.println();

        System.out.println("Задача 5 Русский текст russian.txt");
        Path russianPath = Paths.get("russian.txt");
        if (Files.exists(russianPath) && Files.isReadable(russianPath)) {
            try {
                RussianConsonantsProcessor.processFile(russianPath);
            } catch (IOException e) {
                System.err.println("Ошибка при чтении russian.txt: " + e.getMessage());
            }
        } else {
            System.out.println("Файл russian.txt не найден или недоступен — пропускаем задачу 5");
        }
        System.out.println();

        System.out.println("Задача 6 Печать в обратном порядке элементов очереди");
        Deque<String> queue = new ArrayDeque<>();
        queue.add("первый");
        queue.add("второй");
        queue.add("третий");
        System.out.println("Исходная очередь: " + queue);
        QueuePrinter.printReverse(queue);
        System.out.println();

        System.out.println("Задача 7 Стримы с Point -> Polyline");
        List<Point> points = Arrays.asList(
                new Point(1, -2),
                new Point(2, 3),
                new Point(1, -2),
                new Point(0, -1),
                new Point(3, -4)
        );
        System.out.println("Исходные точки - " + points);
        Polyline polyline = PointProcessor.buildPolyline(points);
        System.out.println("Результирующая ломаная - " + polyline);
        System.out.println();

        System.out.println("Задача 8 Группировка людей по номеру people.txt");
        Path peoplePath = Paths.get("people.txt");
        if (Files.exists(peoplePath) && Files.isReadable(peoplePath)) {
            try {
                PeopleGrouper.processPeopleFile(peoplePath);
            } catch (IOException e) {
                System.err.println("Ошибка при чтении people.txt: " + e.getMessage());
            }
        } else {
            System.out.println("Файл people.txt не найден или недоступен — пропускаем задачу 8");
        }
    }
}

interface Fractionable {
    double getDouble();
    void setNumerator(int n);
    void setDenominator(int d);
}

class Fraction implements Fractionable {
    private int numerator;
    private int denominator; // всегда >= 0
    private Double cachedDouble = null;

    public Fraction(int numerator, int denominator) {
        if (denominator == 0) throw new IllegalArgumentException("Знаменатель не может быть 0"); // переносим знак в числитель, если знаменатель отрицательный
        if (denominator < 0) {
            denominator = -denominator;
            numerator = -numerator;
        }
        this.numerator = numerator;
        this.denominator = denominator;
    }

    public int getNumerator() { return numerator; }
    public int getDenominator() { return denominator; }

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
            this.cachedDouble = null; // инвалидируем кеш
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

    @Override
    public String toString() {
        return numerator + "/" + denominator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Fraction)) return false;
        Fraction other = (Fraction) o;
        return this.numerator == other.numerator && this.denominator == other.denominator;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numerator, denominator);
    }
}

interface Meowable {
    void meow();
}

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

    @Override
    public String toString() {
        return "кот: " + name;
    }
}

class Funs {
    // Метод принимает Meowable и вызывает meow() у него несколько раз.
    public static void meowsCare(Meowable m) {
        if (m == null) throw new IllegalArgumentException("Meowable не может быть null.");
        for (int i = 0; i < 5; i++) {
            m.meow();
        }
    }
}

class CollectionsUtil {
    public static <T> List<T> symmetricDifference(Collection<T> a, Collection<T> b) {
        if (a == null || b == null) throw new IllegalArgumentException("Коллекции не могут быть null.");
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

class ApplicantsProcessor {
    static class Applicant {
        final String surname;
        final String name;
        final int score1;
        final int score2;
        Applicant(String surname, String name, int score1, int score2) {
            this.surname = surname;
            this.name = name;
            this.score1 = score1;
            this.score2 = score2;
        }
        @Override
        public String toString() {
            return surname + " " + name + " (" + score1 + ", " + score2 + ")";
        }
    }

    public static void processApplicantsFile(Path path) throws IOException {
        try (BufferedReader br = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String first = br.readLine();
            if (first == null) {
                System.out.println("Файл applicants.txt пуст.");
                return;
            }
            int n;
            try {
                n = Integer.parseInt(first.trim());
            } catch (NumberFormatException e) {
                System.out.println("Первая строка должна содержать целое число N.");
                return;
            }
            if (n < 0 || n > 500) {
                System.out.println("N должно быть в диапазоне 0..500. Текущее значение: " + n);
                return;
            }
            List<Applicant> applicants = new ArrayList<>();
            int readLines = 0;
            while (readLines < n) {
                String line = br.readLine();
                readLines++;
                if (line == null) break;
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split("\\s+");
                if (parts.length < 4) {
                    System.out.println("Строка пропущена (не соответствует формату): " + line);
                    continue;
                }
                String surname = parts[0];
                String name = parts[1];
                if (surname.length() > 20 || name.length() > 15) {
                    System.out.println("Пропущено (нарушение длины): " + line);
                    continue;
                }
                int s1, s2;
                try {
                    s1 = Integer.parseInt(parts[2]);
                    s2 = Integer.parseInt(parts[3]);
                } catch (NumberFormatException e) {
                    System.out.println("Пропущено (баллы не числа): " + line);
                    continue;
                }
                if (s1 < 0 || s1 > 100 || s2 < 0 || s2 > 100) {
                    System.out.println("Пропущено (баллы вне диапазона 0..100): " + line);
                    continue;
                }
                applicants.add(new Applicant(surname, name, s1, s2));
            }

            System.out.println("Абитуриенты, не допущенные к сдаче в первом потоке (балл < 30 по любому предмету):");
            applicants.stream()
                    .filter(a -> a.score1 < 30 || a.score2 < 30)
                    .forEach(a -> System.out.println(a.surname + " " + a.name));
        }
    }
}

class RussianConsonantsProcessor {
    private static final Set<Character> VOICED = Set.of(
            'б','в','г','д','ж','з','л','м','н','р'
    );

    public static void processFile(Path path) throws IOException {
        String text = Files.readString(path, StandardCharsets.UTF_8);
        String cleaned = text.replaceAll("[^\\p{L}\\-\\s]+", " ");
        String[] rawWords = cleaned.split("\\s+");
        Map<Character, Set<String>> letterToWords = new HashMap<>();
        for (String w : rawWords) {
            if (w == null) continue;
            String word = w.trim().toLowerCase(Locale.ROOT);
            if (word.isEmpty()) continue;
            // учитываем каждую букву уникально в слове
            Set<Character> lettersInWord = new HashSet<>();
            for (int i = 0; i < word.length(); i++) {
                char ch = word.charAt(i);
                if (VOICED.contains(ch)) lettersInWord.add(ch);
            }
            for (char ch : lettersInWord) {
                letterToWords.computeIfAbsent(ch, k -> new HashSet<>()).add(word);
            }
        }
        List<Character> result = letterToWords.entrySet().stream()
                .filter(e -> e.getValue().size() > 1)
                .map(Map.Entry::getKey)
                .sorted()
                .collect(Collectors.toList());

        if (result.isEmpty()) {
            System.out.println("Не найдено звонких согласных, которые входят более чем в одно слово");
        } else {
            System.out.print("Звонкие согласные, которые входят более чем в одно слово: ");
            result.forEach(c -> System.out.print(c + " "));
            System.out.println();
        }
    }
}

class QueuePrinter {
    public static <T> void printReverse(Queue<T> queue) {
        if (queue == null) {
            System.out.println("Очередь null");
            return;
        }
        if (queue.isEmpty()) {
            System.out.println("Очередь пуста");
            return;
        }
        Deque<T> stack = new ArrayDeque<>();
        for (T elem : queue) stack.push(elem);
        System.out.print("Элементы в обратном порядке: [");
        boolean first = true;
        while (!stack.isEmpty()) {
            if (!first) System.out.print(", ");
            System.out.print(stack.pop());
            first = false;
        }
        System.out.println("]");
    }
}

class Point {
    private final double x;
    private final double y;
    public Point(double x, double y) { this.x = x; this.y = y; }
    public double getX() { return x; }
    public double getY() { return y; }

    public Point withY(double newY) { return new Point(this.x, newY); }

    @Override
    public String toString() { return "{" + x + ";" + y + "}"; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point)) return false;
        Point p = (Point) o;
        return Double.compare(x, p.x) == 0 && Double.compare(y, p.y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}

class Line {
    private final Point start;
    private final Point end;
    public Line(Point start, Point end) { this.start = start; this.end = end; }
    @Override
    public String toString() {
        return "Линия от " + start + " до " + end;
    }
}

class Polyline {
    private final List<Point> points;
    public Polyline(List<Point> points) {
        if (points == null) throw new IllegalArgumentException("points == null");
        this.points = new ArrayList<>(points);
    }
    @Override
    public String toString() {
        return "Линия " + points;
    }
}

class PointProcessor {
    public static Polyline buildPolyline(List<Point> points) {
        if (points == null) throw new IllegalArgumentException("points == null");
        // Берём уникальные точки (по X,Y), отсортируем по X, сделаем Y = |Y| если было отрицательное
        List<Point> result = points.stream()
                .filter(Objects::nonNull)
                .distinct()
                .sorted(Comparator.comparingDouble(Point::getX))
                .map(p -> p.getY() < 0 ? p.withY(-p.getY()) : p)
                .collect(Collectors.toList());
        return new Polyline(result);
    }
}

class PeopleGrouper {
    public static void processPeopleFile(Path path) throws IOException {
        List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
        Map<Integer, List<String>> grouped = new TreeMap<>();
        for (String raw : lines) {
            if (raw == null) continue;
            String line = raw.trim();
            if (line.isEmpty()) continue;
            String[] parts = line.split(":", 2);
            String rawName = parts.length > 0 ? parts[0].trim() : "";
            String numPart = parts.length > 1 ? parts[1].trim() : "";
            if (rawName.isEmpty()) continue;
            if (numPart.isEmpty()) continue; // убрать людей без номеров
            Integer num = null;
            try {
                num = Integer.parseInt(numPart);
            } catch (NumberFormatException e) {
                continue; // пропускаем некорректные номера
            }
            String name = normalizeName(rawName);
            grouped.computeIfAbsent(num, k -> new ArrayList<>()).add(name);
        }
        System.out.println("Группировка по номеру (номер: [имена]):");
        System.out.println(grouped);
    }

    private static String normalizeName(String raw) {
        String lower = raw.toLowerCase(Locale.ROOT).trim();
        if (lower.isEmpty()) return lower;
        return Character.toUpperCase(lower.charAt(0)) + lower.substring(1);
    }
}