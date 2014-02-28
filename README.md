Project: Cryptoby
========

__Cryptoby__ is a simple Java Tool for end- and decryption of any files.

It uses two forms of encryption:  
* A __symmetric__ encryption based on AES-256 (http://www.cryptopp.com/wiki/Aes)
* A __asymmetric__ encryption based on ECDH (http://www.cryptopp.com/wiki/ECMQV)

These cryptograhic algorithms are directly implemented in the source code of the tool.  
That means, it doesn't use any cryptographic libraries from Java or elsewhere. Further needed stuff is implemented:

* __Prime Number Test__ with Miller-Rabin (http://de.wikipedia.org/wiki/Miller-Rabin-Test)
* __Key Generator__ with SHA-3 (http://de.wikipedia.org/wiki/SHA-3)

## Project Setup

1. Project Methode: Scum (http://app.yodiz.com/home/pages/all-project.vz?cid=10160&pid=1)
2. IDE: Netbeans 8 beta
3. Language: Java (JDK 1.7)
4. Build-Management-Tool: Maven
5. Design Pattern: Stragety http://de.wikipedia.org/wiki/Strategie_(Entwurfsmuster)

## Testing

_How do I run the project's automated tests?_

### Unit Tests

1. `rake spec`

### Integration Tests

1. _Run other local services / provide credentials for external services._
2. `rake spec:integration`

## Deploying

### _How to setup the deployment environment_

- _Required heroku addons, packages, or chef recipes._
- _Required environment variables or credentials not included in git._
- _Monitoring services and logging._

### _How to deploy_

## Troubleshooting & Useful Tools

_Examples of common tasks_

> e.g.
> 
> - How to make curl requests while authenticated via oauth.
> - How to monitor background jobs.
> - How to run the app through a proxy.

## Contributing changes

- _Internal git workflow_
- _Pull request guidelines_
- _Tracker project_
- _Google group_
- _irc channel_
- _"Please open github issues"_


Links:
* http://www.codeplanet.eu/tutorials/cpp/51-advanced-encryption-standard.html AES implementation explained
* http://www.codeplanet.eu/files/flash/Rijndael_Animation_v4_eng.swf Flash AES eexplained
* http://en.wikipedia.org/wiki/Pseudorandom_number_generator#Cryptographically_secure_pseudorandom_number_generators
