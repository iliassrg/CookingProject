                                                      ΕΡΓΑΣΙΑ JAVA, REPORT
Για την περάτωση του πρώτου μέρους της εργασίας συνεργάστηκαν οι εξής φοιτητές(βάσει των αριθμών μητρώων τους) :
2023124
2023069
2023089


--- ΟΔΗΓΙΕΣ ΕΚΤΕΛΕΣΗΣ ---
1. Ανοίγουμε το terminal
2. Πάμε στο directory της εργασίας και εκτελούμε την εντολή: cd target (όπου βρίσκεται η main)
3. Εκτελούμε την εντολή: chcp 65001, για να αναγνωρίζει το πρόγραμμα την ελληνική γλώσσα.
4. Τέλος, εκτελούμε την εντολή: java -jar CookingProject-1.0-SNAPSHOT.jar pancakes.cook french_fries.cook syrup.cook fakes.cook 


Υλοποιήθηκαν 5 κλάσεις:
Η Main, από την οποία εκτελείται το πρόγραμμα.
Η Recipe, η οποία είναι αφηρημένη κλάση (abstract class) λειτουργεί ως βάση για τις κλάσεις Ingredients, Utensils και Time,
όπου κάθε μια εφαρμόζει την μη αφηρημένη μέθοδο loadFromFile, με την οποία φορτώνονται τα δεδομένα από το αρχείο με τον ίδιο τρόπο σε κάθε κλάση.
Επιπλέον οι μέθοδοι processLine και Display είναι αφηρημένες διότι αναλόγως την κλάση παίρνουν διάφορες μορφές (πολυμορφισμός).
Η Ingredients, η κλάση αυτή είναι υποκλάση της Recipe και βρίσκει και εκτυπώνει κάθε υλικό από το αρχείο συνταγής που δόθηκε από τον χρήστη.
Η Utensils, ομοίως η κλάση αυτή είναι υποκλάση της Recipe και βρίσκει και εκτυπώνει κάθε σκεύος από το αρχείο συνταγής που δόθηκε από τον χρήστη.
Η Time, επίσης είναι υποκλάση της Recipe και βρίσκει και εκτυπώνει τον συνολικό χρόνο που απαιτείται για την εκτέλεση μιας συνταγής σύμφωνα με το αρχείο συνταγής που δόθηκε από τον χρήστη.

Τέλος ,για την υλοποίηση του πρώτου μέρους της εργασίας να αναφέρουμε ότι σε ορισμένα σημεία του πήραμε τη βοήθεια του https://chatgpt.com/ .
Επίσης ,τις συνταγές για τα αρχεία french_fries.cook, fakes.cook και syrup.cook τις αντλήσαμε από τα  site:  https://www.argiro.gr/recipe/tiganites-patates/ ,     https://www.gastronomos.gr/syntagh/fakes-soypa-vinteo/122198/ και https://akispetretzikis.com/recipe/569/ta-melomakarona-toy-akh αντίστοιχα . 