# Simple-NLG-IT
  
  Why this forked version of SimpleNLG-IT 
  -----------------------------
  This fork of Simple-NLG-IT uses a slightly different package name
  unique for the italian language. This is useful for avoiding
  package name collision in case you want to use other SimpleNLG libraries
  built for other languages.

  
  
  SimpleNLG-IT 
  -----------------------------
  Original version of SimpleNLG-IT (https://github.com/alexmazzei/SimpleNLG-IT)

  SimpleNLG-IT is an Italian adaptation of SimpleNLG-EnFr 1.1, that is
  a bilingual English/French adaption of SimpleNLG v4.2. SimpleNLG-IT
  has been developed by Alessandro Mazzei, Cristina Battaglino and
  Cristina Bosco. Plese cite -> INLG16_TOAPPEAR if you use this
  software.

+ SimpleNLG (https://github.com/simplenlg/simplenlg) is a Java library
  for text surface realization in English. It was originally developed
  by Ehud Reiter, Albert Gatt and Dave Westwater, of Aberdeen
  University. If you do not know what is "text surface realization",
  read this http://homepages.abdn.ac.uk/e.reiter/pages/book.html

+ SimpleNLG-EnFr 1.1 (https://github.com/rali-udem/SimpleNLG-EnFr) was
  originally developed by Pierre-Luc Vaudry.

+ Soon we add a more documents DOCS directory. In the while, follow
  the examples provided in the >>package tutorial.italian;<<

  ++ A very simple multilingual start:
  1. cd DOCS
  2. javac -cp .:./simplenlg_it.jar MultiLingualTest.java
  3. java -cp .:./simplenlg_it.jar MultiLingualTest --> "Trilingual love ..."

+ SimpleLEX-IT, that is the lexicon used in SimpleNLG-IT, has a
  specific github page: https://github.com/alexmazzei/SimpleLEX-IT