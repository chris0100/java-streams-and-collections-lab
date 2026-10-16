# Java Streams & Collections Lab

Stream and collection exercises with realistic data (orders, customers), plus a cheat sheet on choosing collections.

| Topic | Class |
|-------|-------|
| `groupingBy`, `partitioningBy`, `toMap` with merge, `flatMap`, `teeing`, top-N | `OrderReports` |
| Custom collector (running statistics) | `OrderReports.revenueStats` |
| Choosing the right collection and its complexity | `CollectionChoices` |

## Run

```bash
mvn test
```

## Cheat sheet: which collection?

| Need | Use | Why |
|------|-----|-----|
| Ordered list, access by index | `ArrayList` | O(1) get, amortized O(1) add |
| Unique elements, fast `contains` | `HashSet` | O(1) average |
| Unique + sorted | `TreeSet` | O(log n), range queries |
| Key -> value lookup | `HashMap` | O(1) average |
| Keep insertion order | `LinkedHashMap` / `LinkedHashSet` | predictable iteration |
| Queue / stack | `ArrayDeque` | faster than `Stack` and `LinkedList` |
| Priority (top-N, scheduling) | `PriorityQueue` | O(log n) offer/poll |
| Shared between threads | `ConcurrentHashMap`, `CopyOnWriteArrayList`, `BlockingQueue` | thread-safe without a global lock |

## Interview notes

- Streams are lazy: nothing runs until a terminal operation (`collect`, `forEach`, `count`...).
- `toMap` throws on duplicate keys unless you pass a merge function.
- `parallelStream` uses the common ForkJoinPool: only for CPU-bound work on big data, never for blocking I/O.
- `equals`/`hashCode` must be consistent for objects used in `HashSet`/`HashMap` keys.
- Prefer `List.of`/`Map.of` (immutable) for constants; they reject `null`.
