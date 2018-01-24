# Fotograf
Projekt Fotograf to aplikacja internetowa przeznaczona dla wszystkich użytkowników, którzy poszukują dobrego fotografa w swojej okolicy, oraz fotografów, którzy chcieliby zareklamować swoje usługi. Strona jest dostępna po Polsku, oraz po Angielsku.
Backend aplikacji został napisany w Javie, z frameworkiem Spring, a Frontend w JSP z wykorzystaniem technologii HTML, JQuery, JavaScriptu i CSS. Baza danych korzysta z silnika MySQL, a łączymy się z nią poprzez Hibernate i JPA.  

Aplikacja umożliwia klientom znalezienie dobrego fotografa i umówienie się z nim na sesję zdjęciową, a fotografom pozwala pozyskiwać nowych klientów, i reklamować swoje usługi w internecie. Każdy użytkownik ma dostęp do wyszukiwarki, i przeglądania albumów. Użytkownik zarejestrowany jako klient może oprócz tego zarządzać swoim kontem, komentować i oceniać albumy, oraz składać zamówienia.
Użytkownik który zarejestruje się jako fotograf ma takie same uprawnienia jak klient, ale oprócz tego może tworzyć własne albumy, widnieje w wynikach wyszukiwania, oraz może otrzymywać zamówienia.  

Główna strona wita nas listą 0-10 najlepiej ocenianych albumów, posortowaną od najwyższej oceny do najniższej. Jeśli w bazie znajduje się mniej niż 10 albumów to wyświetlane są one wszystkie.  
Użytkownik ma możliwość wyszukiwania fotografów wg. nazwy użytkownika oraz miasta, lub po dowolnej kombinacji danych: imię, nazwisko, nazwa użytkownika, miasto. Po wybraniu fotografa wyświetla się jego profil, w którym wyświetlone są jego podstawowe dane - imię, nazwisko i avatar, oraz lista albumów utworzonych przez danego fotografa.   
Po przejściu do dowolnego albumu możemy przeglądać zdjęcia które się w nim znajdują, przeczytać opis umieszczony przez fotografa, przeglądać komentarze, oraz sprawdzić średnią ocenę użytkowników. Jako zalogowany użytkownik mamy również możliwość skomentowania albumu i wystawienia własnej oceny. Jako fotograf i autor albumu możemy go dowolnie modyfikować - dodawać, usuwać i edytować zdjęcia, oraz zmieniać opis i nazwę albumu.  
Zalogowany użytkownik na stronie profilu fotografa widzi również przycisk prowadzący do formularza składania zamówienia. Składanie zamówienia polega na wybraniu daty i godziny sesji fotograficznej, oraz zawarciu ewentualnych uwag. Po złożeniu zamówienia przez klienta, obie strony są informowane o tym fakcie mailem. Fotograf ma wtedy 3 możliwości może zaakceptować, odrzucić, lub zmodyfikować zamówienie. Po modyfikacji zamówienia przez fotografa, klient musi zaakceptować lub odrzucić wprowadzone zmiany. Każda modyfikacja statusu zamówienia prowadzi do wysłania maila do odpowiedniej osoby - fotografa, klienta, lub obu z nich.   
Każdy zarejestrowany użytkownik może edytować swoje dane w serwisie - imię, nazwisko, miasto, nr telefonu, adres e-mail itd. Oprócz tego może też dodać/zmienić swój avatar, oraz zmienić hasło do swojego konta.  

## Diagram UseCase
![UseCase](Dokumentacja/UseCase.png)
## Diagram ERD
![DbSchema](Dokumentacja/ERD.png)
## Diagram klas
![ClassDiagram](Dokumentacja/classDiagram.png)
