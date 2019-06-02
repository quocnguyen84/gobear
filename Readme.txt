- Ensure that you have installed Java. My version is 1.8.0_191
- Ensure that you have installed Maven and be able to execute 'mvn' command from the console. My version is 3.6.1
- Open the console, navigate to project root folder (folder which has pom.xml file). Execute 'mvn clean test' and waiting the automation test finishes executing
Test Scenario is below:
Step 01: Navigate to gobear.com
Step02: Click on href with '#Insurance' value to ensure we are at Insurance section
Step03: click on href with '#Travel' value to ensure we are at Travel Insurance section
Step04: Keep default value and click on 'Show My Result' button. Verify that there are at least 3 results
Step05: Click on 'Promos only' option
Step06: 'Clear All' filter
Step07: Click on filter insurer 'Malayan Insurance' option. Verify that every result has title 'Malayan Insurance' in order to verify that filter is worked.
Step08: Clear the filter 'Malayan Insurance' by clicking 'Clear All' filter
Step09: Click on 'See More' button to have additional conditional
Step09A: Move left slider of 'Medical expenses while traveling' to 30 offset. Get the min value of 'Medical expenses while traveling' slider after that. Verify that every result has 'Medical expenses while traveling' more than min value to verify that the filter is worked.
Step09B: Click on 'See Less' button
Step10: Click on 'annual trip' detail option.
Step10A: Verify that the 1st result supports 'annual trip' (actually, all results should be verified but in this exercise, only 1 result is verified for demo purpose)
Step10B: Go back to previous page.
Step11: Click on '2 persons' detail option
Step12: 'Clear All' filter
Step13: Select 'Price: High to low' sort option. Verify that the result is sorted descending.

- All step screenshots can be viewed on 'screenshots' folder
- The execution recording on my laptop can be viewed at https://drive.google.com/file/d/1tmn8ZxlafsWiyzFkzSyk0b40NJe9h_uh/view