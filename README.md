# Project Walk

### Team Members
* Kyle
* Tolga
* Bau
* Svetoslav
* Tautvilas
* Mofe

## Screencast Checklist

-[ ] Talk about reason for choosing data type

    * Mention that Teachers, Students and Education ministers are our target audience
    * We are covering the UK only
    * The data spans between 1991 - 2011

-[ ] Basic display of data

    * Explain our choice of data, pull out drawer component and show key and information
    * State difference between red line and green columns

-[ ] Press a point on the red line

    * Explain how the left fragment shows the unemployment data for that year
    * Show the comparison arrows and pie chart to do with male and female unemployment
    
-[ ] Press a green column

    * Do the same for Education investment
    * Show the logo for government party changing based on year
    
-[ ] Data Caching

    * Turn off wifi/mobile data
    * Relaunch app
    * Speak about how the data is loaded from the Application's Android cache directory
    * Mention that we felt an SQL database would be overkill for an application of this size
    * More efficient to store the response string in a string buffer to be interpreted as normal
      if wifi or mobile data isn't available

## Development
- Meet during lab times to work on features

### Conventions
 - Class names = ClassName extends OtherClass
 - Variables = String variableName = "";
 - Static final fields = FINAL_FIELD = 10;
 - JavaDoc public methods

## Pull Requests
- When a feature is completed, open a PR on Github marking it with `Ready` label
- Another team member will review it and make any comments, which the developer can then address
- If features need to be tweaked or added to
- Once happy, the PR can be merged into master.
- No pushing direct to `Master` under ANY circumstances

### Pull Request Criterion
- Feature must function as expected
- Automated tests for both logic and UI must be present and must all pass

### How to rebase from master to resolve conflicts
_Assuming you are working on branch called `my_branch`_

1. `$ git checkout master`
2. `$ git pull origin master`
3. `$ git checkout my_branch`
4. `$ git rebase master`
5. For each conflict, the terminal will state which file the conflict is in.
6. The top part of the conflict is what's in the head of the repository, the bottom is what is on `my_branch`
7. Remove the part which is outdated/not needed anymore, or if you didn't touch the code, leave what is on `Master`
8. Once all conflicts are resolved:
9. `$ git add -A && git rebase --continue`
10. if there are further conflicts then repeat steps 5 -> 9
11. Once the rebase has finished: 
    * `git commit -m "Commit message"`
    * `git push -f origin my_branch`

This will force push the correct changes to your branch.

The PR can then be merged once the other criterion are met

## Testing
- For views, test they they are present and they respond to user actions
- Test all methods that have logic
- Test methods that have a return value to ensure what is being returned is what is expected.
