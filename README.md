Project: Cryptoby
========

__Cryptoby__ is a simple Java Tool for en- and decryption of any files.

It uses two forms of encryption:  
* A __symmetric__ encryption based on AES-256 (http://www.cryptopp.com/wiki/Aes)
* An __asymmetric__ encryption based on RSA (https://de.wikipedia.org/wiki/RSA-Kryptosystem)

These cryptograhic algorithms are directly implemented in the source code of the tool.  
That means, it doesn't use any cryptographic libraries from Java or elsewhere. Further needed stuff is implemented:

* __Prime Number Test__ with Miller-Rabin (http://de.wikipedia.org/wiki/Miller-Rabin-Test)
* __Key Generator__ with SHA-3 (http://de.wikipedia.org/wiki/SHA-3)

## Project Setup

1. Project Methode: Scrum (http://app.yodiz.com/home/pages/all-project.vz?cid=10160&pid=1)
2. Documentations on Google Drive: https://drive.google.com/#folders/0B1I433N3y2uSOHZfTGlsUG83c00
2. IDE: Netbeans 8
3. Language: Java (JDK 1.7)
4. Build-Management-Tool: Maven
5. Design Pattern: Stragety http://de.wikipedia.org/wiki/Strategie_(Entwurfsmuster)

## Testing

### JUnit Tests

Created JUnit Tests for every public method in all Classes except UI Classes.

### Manuel Tests

UI Classes and Methodes are tested manual.

## Pendent Issues

1. Performance issues with creating big RSA keys and RSA decryption
2. Sometimes problem with large memory usage

### Importent Links:
* http://www.codeplanet.eu/tutorials/cpp/51-advanced-encryption-standard.html AES implementation explained
* http://www.codeplanet.eu/files/flash/Rijndael_Animation_v4_eng.swf Flash AES eexplained
* http://en.wikipedia.org/wiki/Pseudorandom_number_generator#Cryptographically_secure_pseudorandom_number_generators
