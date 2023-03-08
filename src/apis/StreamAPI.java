package apis;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.function.BinaryOperator.maxBy;

public class StreamAPI {

    /**
     * בכוךם צריך לשים לב שאם "סגרנו" כבר את ה stream אז לא נוכל להשתמש בו יותר
     */

    public static void main(String[] args) {
        /*
        createStream();
        collectionToStream();
        convertStreamToDS();
        count_min_max();
        average();
        reduce();
        find();
        forEach();
        match();
        collect();
        skip();
        limit();
        generate_iterate();
        distinct();
        sorted();
        filter();
        map();
        peek();
        flatMap();
        joining();
        summaryStatistic();
        partitionBy();
        groupingBy();
        take_drop_while();
        parallel();
        */
    }

    static void createStream() {
        Stream<Integer> stream = Stream.of(5, 1, 2, 66, -5);
        // special Stream types
        IntStream intStream = IntStream.of(5, 1, 2, 66, -5);
        // like range() in python
        IntStream rangeStream = IntStream.range(1, 10); // 1-9
        IntStream rangeClosedStream = IntStream.rangeClosed(1, 10); // 1-10

        // Stream.Builder
        Stream<String> stringStream = Stream.<String>builder().add("a").add("b").build();
    }

    static void collectionToStream() {
        Integer[] arr = {5, 1, 2, 66, -5};
        Stream<Integer> streamFromArray = Arrays.stream(arr);

        List<Integer> list = Arrays.asList(arr);
        Stream<Integer> streamFromList = list.stream();
    }

    static void convertStreamToDS() {
        Stream<Book> bookStream = Book.getBookStream();

        Object[] objectArray = bookStream.toArray();

        Stream<Book> bookStream2 = Book.getBookStream();
        Book[] BookArray2 = bookStream2.toArray(Book[]::new);


    }

    static void count_min_max() {
        Stream<Book> bookStream = Book.getBookStream();

        long count = bookStream.count(); //5

        Stream<Book> bookStream2 = Book.getBookStream();
        Optional<Book> mostExpensiveBook = bookStream2.max(comparing(Book::price)); // lordOfRings
        if (mostExpensiveBook.isPresent()) {
            Book b = mostExpensiveBook.get();
        }

        Stream<Book> bookStream3 = Book.getBookStream();
        Optional<Book> alphabeticallyFirstBook = bookStream3.min(comparing(Book::title)); // gameOfThrones
        alphabeticallyFirstBook.ifPresent(System.out::println);
    }

    static void average() {
        OptionalDouble average = Arrays.stream(new int[]{1, 5, 4, 2, 6, 7}).average();

        Arrays.stream(new int[]{1, 5, 4, 2, 6, 7}).average().ifPresent(System.out::println);
    }

    static void reduce() {
        Integer sumOfIntegers = Stream.of(1, 2, 3).reduce(0, Integer::sum);

        List<String> letters = Arrays.asList("a", "b", "c");
        String res = letters.stream().reduce("", (prev, curr) -> prev + curr); // abc

        Stream<Book> bookStream = Book.getBookStream();
        double totalPrice =
                bookStream.reduce(
                        .0,
                        (cumulativePriceResult, currentBook) -> cumulativePriceResult + currentBook.price(),
                        Double::sum
                );

    }

    static void find() {
        Stream<Book> bookStream = Book.getBookStream();
        Optional<Book> firstBook = bookStream.findFirst();

        Stream<Book> bookStream2 = Book.getBookStream();
        Optional<Book> anyBook = bookStream2.findAny();
    }

    static void forEach() {
        Stream<Book> bookStream = Book.getBookStream();
        bookStream.forEach(System.out::println);
    }

    static void match() {
        Stream<Integer> stream = Stream.of(25, 15, 75, 35, 40, 5, 65);
        boolean oneIsEven = stream.anyMatch(el -> el % 2 == 0); // true

        Stream<Integer> stream2 = Stream.of(25, 15, 75, 35, 40, 5, 65);
        boolean AllAerEven = stream2.allMatch(el -> el % 2 == 0); // false

        Stream<Integer> stream3 = Stream.of(25, 15, 75, 35, 40, 5, 65);
        boolean allAreLessThan80 = stream3.noneMatch(el -> el > 80); // true
    }

    static void collect() {
        Stream<Book> bookStream = Book.getBookStream();
        Stream<Book> bookStream2 = Book.getBookStream();
        Stream<Book> bookStream3 = Book.getBookStream();
        Stream<Book> bookStream4 = Book.getBookStream();

        List<Book> bookList = bookStream.toList();
        Set<Book> bookSet = bookStream2.collect(Collectors.toSet());
        Vector<Book> bookVector = bookStream3.collect(Collectors.toCollection(Vector::new));
        LinkedList<Book> bookLinkedList = bookStream4.collect(Collectors.toCollection(LinkedList::new));
    }

    static void skip() {
        Stream<Book> bookStream = Book.getBookStream();
        Optional<Book> thirdBook = bookStream.skip(2).findFirst();//harryPotter
        thirdBook.ifPresent(System.out::println);
    }

    static void limit() {
        Stream<Book> bookStream1 = Book.getBookStream();
        Stream<Book> firstTwoBooks = bookStream1.limit(2); // lordOfRings, hobbit


        Stream<Book> bookStream2 = Book.getBookStream();
        Stream<Book> secondAndThirdBooks = bookStream2.skip(1).limit(2);//hobbit, harryPotter
    }

    // create infinite stream
    static void generate() {
        List<Double> TenRandNum =
                Stream.generate(Math::random)
                        .limit(10)
                        .toList();

        // print "wow" 3 times
        Stream.generate(() -> "wow")
                .limit(3)
                .forEach(System.out::println);
    }

    static void iterate() {
        List<Integer> intFrom0To9 =
                Stream.iterate(0, n -> n + 1)
                        .limit(10)
                        .toList();


        // print the 10 first powers of 2
        Stream.iterate(2, n -> n * 2)
                .limit(10)
                .forEach(System.out::println);

        // like for loop
        Stream.iterate(1, n -> n < 20, n -> n + 2).forEach(System.out::println);
    }

    static void distinct() {
        // unique values
        Stream.of(10, 10, 10, 20, 30).distinct().forEach(System.out::println); // 10,20,30
    }

    static void sorted() {
        List<Integer> sortedInts = Stream.of(25, 15, 75, 35, 40, 5, 65).sorted().toList();

        //sort by object data fields
        Stream<Book> bookStream = Book.getBookStream();
        bookStream.sorted(comparing(Book::price).thenComparing(Book::title))
                .forEach(System.out::println);
    }

    static void filter() {
        List<Integer> filteredList = Stream.of(25, 15, 75, 35, 40, 5, 65).filter(el -> el < 30).toList();
    }

    static void map() {
        List<Integer> listTimes10 =
                Stream.of(25, 15, 75, 35, 40, 5, 65)
                        .map(n -> n * 10).toList();

        Stream<Book> bookStream = Book.getBookStream();
        List<String> titles = bookStream.map(Book::title).toList();
    }

    // כדי לעשות כמה דברים עם אותו stream
    static void peek() {
        Stream<Book> bookStream1 = Book.getBookStream();
        List<Book> printAndCollect = bookStream1.peek(System.out::println).toList();

        List<Person> people =
                Person.getPersonStream()
                        .peek(person -> person.setAge(person.getAge() + 1))
                        .toList();
        System.out.println(people);
    }

    static void flatMap() {
        List<List<Integer>> nestedList = Arrays.asList(
                Arrays.asList(1, 2, 3), Arrays.asList(4, 5, 6)
        );

        List<Integer> mergeList =
                nestedList.stream()
                        .flatMap(Collection::stream)
                        .toList();
    }


    static void joining() {
        Stream<Book> bookStream = Book.getBookStream();
        String commaSeparatedTitles = bookStream.map(Book::title).collect(Collectors.joining(", "));
    }

    static void summaryStatistic() {
        Stream<Book> bookStream = Book.getBookStream();
        DoubleSummaryStatistics summaryStatistics = bookStream.collect(Collectors.summarizingDouble(Book::price));
        System.out.println(summaryStatistics.getCount());
        System.out.println(summaryStatistics.getSum());
        System.out.println(summaryStatistics.getMin());
        System.out.println(summaryStatistics.getAverage());
        System.out.println(summaryStatistics.getMax());
    }

    static void partitionBy() {
        Stream<Integer> integerStream = Stream.of(25, 15, 75, 35, 40, 5, 10, 55, 60, 80);
        Map<Boolean, List<Integer>> evenOddMap = integerStream.collect(Collectors.partitioningBy(i -> i % 2 == 0));
        // {false=[25, 15, 75, 35, 5, 55], true=[40, 10, 60, 80]}

    }

    static void groupingBy() {
        Stream<Book> bookStream = Book.getBookStream();
        Map<String, List<Book>> bookByAuthor = bookStream.collect(Collectors.groupingBy(Book::author));
       /*
            J.R.R. Tolkien=[Book[title=The lord of the Rings, author=J.R.R. Tolkien, price=60.0], Book[title=The Hobbit, author=J.R.R. Tolkien, price=40.0]]
            J.K. Rowling=[Book[title=Harry Potter, author=J.K. Rowling, price=20.0]]
            Dan Brown=[Book[title=Da Vinci Code, author=Dan Brown, price=30.0]]
            G.R.R. Martin=[Book[title=A Song of Ice and Fire, author=G.R.R. Martin, price=50.0]]
        */

        Stream<Book> bookStream2 = Book.getBookStream();
        Map<String, List<String>> titlesByAuthor =
                bookStream2.collect(
                        Collectors.groupingBy(
                                Book::author,
                                Collectors.mapping(Book::title, Collectors.toList())
                        )
                );
        /*
            J.R.R. Tolkien=[The lord of the Rings, The Hobbit]
            J.K. Rowling=[Harry Potter]
            Dan Brown=[Da Vinci Code]
            G.R.R. Martin=[A Song of Ice and Fire]
         */


        Stream<Book> bookStream3 = Book.getBookStream();
        Map<String, Optional<Book>> mostExpensiveByAuthor =
                bookStream3.collect(
                        Collectors.groupingBy(
                                Book::author,
                                Collectors.reducing(maxBy(comparing(Book::price)))
                        ));
        /*
            J.R.R. Tolkien=Optional[Book[title=The lord of the Rings, author=J.R.R. Tolkien, price=60.0]]
            J.K. Rowling=Optional[Book[title=Harry Potter, author=J.K. Rowling, price=20.0]]
            Dan Brown=Optional[Book[title=Da Vinci Code, author=Dan Brown, price=30.0]]
            G.R.R. Martin=Optional[Book[title=A Song of Ice and Fire, author=G.R.R. Martin, price=50.0]]
         */
    }

    static void take_drop_while() {
        Stream<Integer> stream1 = Stream.of(25, 15, 75, 35, 40, 5, 10, 55, 60, 80);
        Stream<Integer> stream2 = Stream.of(25, 15, 75, 35, 40, 5, 10, 55, 60, 80);
        Stream<Integer> stream3 = Stream.of(25, 15, 75, 35, 40, 5, 10, 55, 60, 80);

        // act like filter
        // take all the elements until when the condition is false
        // [25, 15]
        List<Integer> takeWhile = stream1.takeWhile(n -> n < 30).toList();

        // drop all the elements until when the condition is false
        // [75, 35, 40, 5, 10, 55, 60, 80]
        List<Integer> dropWhile = stream2.dropWhile(n -> n < 30).toList();

        //[25, 15, 5, 10]
        List<Integer> filterList = stream3.filter(n -> n < 30).toList();
    }

    static void parallel() {
        IntStream.range(0, 10).parallel().forEach(System.out::println);
    }

    private static record Book(String title, String author, double price) {
        static Stream<Book> getBookStream() {
            Book lordOfRings = new Book("The lord of the Rings", "J.R.R. Tolkien", 60.0);
            Book hobbit = new Book("The Hobbit", "J.R.R. Tolkien", 40.0);
            Book harryPotter = new Book("Harry Potter", "J.K. Rowling", 20.0);
            Book daVinciCode = new Book("Da Vinci Code", "Dan Brown", 30.0);
            Book gameOfThrones = new Book("A Song of Ice and Fire", "G.R.R. Martin", 50.0);

            Stream<Book> bookStream = Stream.of(lordOfRings, hobbit, harryPotter, daVinciCode, gameOfThrones);
            return bookStream;
        }
    }

    @Data
    @AllArgsConstructor
    private static class Person {
        String name;
        int age;

        public Person() {
        }

        static Stream<Person> getPersonStream() {
            return Stream.of(new Person("Shay", 24), new Person("Itzik", 52), new Person("Yossi", 13));
        }
    }


}
