# Report

Submitted report to be manually graded. We encourage you to review the report as you read through the provided
code as it is meant to help you understand some of the concepts. 

## Technical Questions

1. What is the difference between == and .equals in java? Provide a code example of each, where they would return different results for an object. Include the code snippet using the hash marks (```) to create a code block.
   
  * == compares reference equality
   ```java
   public class doubleEqual { 
        public static void main(String[] args) {
            String s1 = new String("hi");
            String s2 = new String("hi");
            System.out.println("doubleEqual: " + (s1 == s2)); // false
        }
   }

   ```
  * equals() compares value equality
    ```java
    public class equals { 
        public static void main(String[] args) {
            String s1 = new String("hi");
            String s2 = new String("hi");
            System.out.println("equals: " + s1.equals(s2)); // true
        }
    }
    ```


2. Logical sorting can be difficult when talking about case. For example, should "apple" come before "Banana" or after? How would you sort a list of strings in a case-insensitive manner? 
     

   * "apple" should come after "Banana", because the ASCII value of the letter 'a' is greater than the ASCII value of the letter 'B'.
   * use `compareToIgnoreCase()` method
      ```java
      public class sort {
          public static void main(String[] args) {
               List<String> words = Arrays.asList("apple", "Banana", "blueberry", "Cherry");
               words.sort((word1, word2) -> word1.compareToIgnoreCase(word2));
   
               System.out.println(words); // [apple, Banana, blueberry, Cherry]
          }
      }
      ```


3. In our version of the solution, we had the following code (snippet)
    
    ```java
    public static Operations getOperatorFromStr(String str) {
        if (str.contains(">=")) {
            return Operations.GREATER_THAN_EQUALS;
        } else if (str.contains("<=")) {
            return Operations.LESS_THAN_EQUALS;
        } else if (str.contains(">")) {
            return Operations.GREATER_THAN;
        } else if (str.contains("<")) {
            return Operations.LESS_THAN;
        } else if (str.contains("=="))...
    ```
    Why would the order in which we checked matter (if it does matter)? Provide examples either way proving your point. 

    * The order we checked matter because `contains()` method checks whether a substring exists in the given string or not. Suppose we have a string that contains ">=" or "<=". If we check for ">" or "<" before ">=" or "<=", it will match the ">" or "<" condition first, leading to a wrong result.


4. What is the difference between a List and a Set in Java? When would you use one over the other? 
    * Set in Java doesn't allow duplicates and the order of elements in the Set is not necessarily the same as the insertion order.
    * List in Java allows duplicates and it maintains the insertion order.
    * If we have duplicates, or we need to maintain the insertion order of elements, we should use List. If we need unique elements and don't care about the order, we should use Set.



5. In [GamesLoader.java](src/main/java/student/GamesLoader.java), we use a Map to help figure out the columns. What is a map? Why would we use a Map here? 
    
    * Map stores key-value pairs, where each key maps a value.
    * We use a Map to store GameData-index pairs to ensure that each column correctly matches its corresponding data in the CSV file.



6. [GameData.java](src/main/java/student/GameData.java) is actually an `enum` with special properties we added to help with column name mappings. What is an `enum` in Java? Why would we use it for this application?

    * `enum` in Java is a special class that stores a group of constants.
    * We use it for this application because the columns in the CSV file is finite and will not change.






7. Rewrite the following as an if else statement inside the empty code block.
    ```java
    switch (ct) {
                case CMD_QUESTION: // same as help
                case CMD_HELP:
                    processHelp();
                    break;
                case INVALID:
                default:
                    CONSOLE.printf("%s%n", ConsoleText.INVALID);
            }
    ``` 

    ```java
    if (ct == CMD_QUESTION || ct == CMD_HELP) {
        processHelp();
    } else {
        CONSOLE.printf("%s%n", ConsoleText.INVALID);
    }
    ```

## Deeper Thinking

ConsoleApp.java uses a .properties file that contains all the strings
that are displayed to the client. This is a common pattern in software development
as it can help localize the application for different languages. You can see this
talked about here on [Java Localization – Formatting Messages](https://www.baeldung.com/java-localization-messages-formatting).

Take time to look through the console.properties file, and change some of the messages to
another language (probably the welcome message is easier). It could even be a made up language and for this - and only this - alright to use a translator. See how the main program changes, but there are still limitations in 
the current layout. 

Post a copy of the run with the updated languages below this. Use three back ticks (```) to create a code block. 

```text
> Task :BGArenaPlanner.main()

*******Huan ying lai dao BoardGame Arena Planner.*******

Zhe shi yi ge bang zhu ren men gui hua zai Board Game Arena shang xiang yao wan de you xi de gong ju

Kai shi shai xuan zhi qian, qing zai xia fang shu ru ni de di yi tiao zhi ling, huo zhe shu ru ? huo help lai cha xuan ke xuan de zhi ling.
> 
```

Now, thinking about localization - we have the question of why does it matter? The obvious
one is more about market share, but there may be other reasons.  I encourage
you to take time researching localization and the importance of having programs
flexible enough to be localized to different languages and cultures. Maybe pull up data on the
various spoken languages around the world? What about areas with internet access - do they match? Just some ideas to get you started. Another question you are welcome to talk about - what are the dangers of trying to localize your program and doing it wrong? Can you find any examples of that? Business marketing classes love to point out an example of a car name in Mexico that meant something very different in Spanish than it did in English - however [Snopes has shown that is a false tale](https://www.snopes.com/fact-check/chevrolet-nova-name-spanish/).  As a developer, what are some things you can do to reduce 'hick ups' when expanding your program to other languages?


As a reminder, deeper thinking questions are meant to require some research and to be answered in a paragraph for with references. The goal is to open up some of the discussion topics in CS, so you are better informed going into industry. 

Localization is crucial in software development as people around the world increasingly depend on software in their daily lives and localization provides a seamless user experience beyond linguistic and cultural boundaries[^1] . According to Ethnologue, there are 7,159 languages are in use today[^2] , but only a small percentage are well-presented in software and digital platforms. 
Wrong localization may lead to customer confusion, cultural insensitivity, brand damage, and even legal issues. Take Gerber's mistake for example[^3] . When they entered the African market with their line of baby food, they used an image of a baby on their packaging. For most of us this seems normal, however in areas where most customers cannot read, the images on packaging is used to describe the contents of the food.
To reduce 'hick ups' when expanding the program to other languages, developers should always use separate resource files to avoid embedding text directly into the code of the software, and always use UTF-8 to support multilingual characters. Also, developers should provide localization notes and use code comments to avoid creating ambiguity due to the lack of context[^4] .

[1^]: https://phrase.com/blog/posts/software-localization/#:~:text=Software%20localization%20is%20the%20process,for%20your%20own%20software%20product. Accessed: 2025-03-01.

[2^]: https://www.ethnologue.com/insights/how-many-languages/. Accessed: 2025-03-01.

[3^]: https://www.argosmultilingual.com/blog/humiliating-examples-when-localization-goes-wrong. Accessed: 2025-03-01.

[4^]: https://phrase.com/blog/posts/10-common-mistakes-in-software-localization/. Accessed: 2025-03-01.