### Downloading
You can download the program from the GitHub repository:
```
git clone https://github.com/Sadra3st/Second-Assignment-BreakingNews.git
cd news-app
```

### Modifications
Ensure you have a valid API key from NewsAPI.org. Replace the placeholder in `Infrastructure.java`:
```java
Infrastructure infrastructure = new Infrastructure("YOUR_API_KEY_HERE");
```

## Executing program
### Dependencies 

* install gradle.
### Running the Program
1. Compile the Java files:
```
javac -d bin src/AP/*.java
```
2. Run the program:
```
java -cp bin AP.Main
```
You can also use intelliJ.

### Step-by-step Execution
1. Choose an option from the menu:
  - View News Articles
  - View Favorite Articles
  - Exit
2. If selecting news, pick an article number to read.
3. Choose to save it as a favorite or go back to the menu.

## Help

### Common Issues
- **API Key Issues:** If the news doesn't load, ensure your API key is correct.
- **File Not Found:** Check if `favorites.txt` exists in the root directory.

## Authors 📝

- **GitHub:** Sadra3st

- **Email:** sadra3st@gmail.com
- **Telegram** @Sadra3st
## Version History

* 0.1
    * A simple program for showing the news
 
* 0.2
    * bugs and errors fixed
    * Hijri calender added
* 0.3
  * The ability to select favorite news and menu was added
* 1.0
  * bugs fixed
  * first release
* 1.1(Latest release)
  * The ability to return to the menu from news list has been added 

## Acknowledgments

Inspiration, code snippets, etc.
* [awesome-readme](https://github.com/matiassingers/awesome-readme)
* [PurpleBooth](https://gist.github.com/PurpleBooth/109311bb0361f32d87a2)
* [w3schools](https://www.w3schools.com/java/java_oop.asp)
* [zenorocha](https://gist.github.com/zenorocha/4526327)
* [Code-With-Z](https://www.youtube.com/watch?v=Yj5PLmHIsCo)
