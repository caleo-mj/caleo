# Caleo

## Table of Contents
1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)
2. [Schema](#Schema)

## Overview
### Description
Tracks the user's calorie intake through identifying different types of food and their associated calorie content, by taking input from the user's camera.

### App Evaluation
- **Category:** Health and Fitness
- **Mobile:** This app would be primarily developed for mobile, as it's more convenient for the user to capture images of their food on their phones. The app can also be viable on a computer via uploading pictures, though the mobile version will be more convenient and easier to use.
- **Story:** Allows the user to take pictures of their food or choose their food type from a list of food categories. The app displays the calorie content of the user's food, tracks the user's daily calorie intake, and helps the user achieve their daily calorie goal.
- **Market:** Any individual can benefit from using this app, especially those who care about their diet and monitoring their calorie intake.
- **Habit:** The app is designed to be used daily in order to acheive a user-specified daily calorie goal. However, a user can use the app just to check the calorie content of a specific food item as often or unoften as they want.
- **Scope:** The scope of this app is local, as you don't need to have an account to use it. The usage and testing of this app doesn't require more than one user to undertake.

## Product Spec

### 1. User Stories (Required and Optional)

**Required Must-have Stories**

- [x] User can enter their name, height, weight, and age to get a recommended daily calorie goal.
- [x] User can view their information and their daily calorie goal.
- [x] User can edit their information and their daily calorie goal.
- [x] User can view their daily intake, thier goal, and the remaining calories to acheive their goal.
- [ ] User can use the device's camera to  capture images of their food
- [ ] User gets back the associated calorie content of their food, and log it in to their daily intake.
- [x] User can choose to log food to their daily log manually by choosing from a list of food items.

**Optional Nice-to-have Stories**

* Send reminders and notifications.
* Recommend food to the user based on their daily goal.
* Gamify the experience: user makes weekly streaks when they achieve thier goal.
* Saves user's frequent food choices to allow the user to pick the same food easily.
* Past daily statistics are available for the user to view them in the Home Feed.

### 2. Screen Archetypes

* Onboarding
    * User can enter their name, height, weight, and age to get a recommended daily calorie goal.
* Profile Screen
    * User can view their information and their daily calorie goal.
* Edit Screen
    * User can edit their information and their daily calorie goal.
* Home Feed
    * User can view their daily intake, thier goal, and the remaining calories to acheive their goal.
* Scan Screen
    * User can use the device's camera to  capture images of their food.
* Detail Screen
    * User gets back the associated calorie content of their food, and log it in to their daily intake.
* Stream Screen
    * User can choose to log food to their daily log manually by choosing from a list of food items.
### 3. Navigation

**Tab Navigation** (Tab to Screen)

* Home Feed
* Scan Screen
* Stream Screen
* Profile

**Flow Navigation** (Screen to Screen)

* Onboarding -> Home Feed (when using app for first time)
* Profile Screen -> Edit Screen
* Scan Screen -> Detail Screen
* Stream Screen -> Detail Screen
* Detail Screen -> Home Feed

## Wireframes
![](https://i.imgur.com/yM61VWt.jpg)


## Schema

### Models

#### Food

   | Property      | Type     | Description |
   | ------------- | -------- | ------------|
   | name      | String   | unique name for food item (default field) |
   | image        | File| image of food item |
   | calories         | Integer     | calorie count of food item |

#### User

   | Property      | Type     | Description |
   | ------------- | -------- | ------------|
   | name      | String   | unique name for the user (default field) |
   | age       | Integer| user age |
   | gender       | String | user gender |
   | weight    | Double     | user weight in kg |
   | height    | Double     | user height in cm |
   | calCount  | Integer    | keeps track of User daily calorie intake
   | calGoal   | Integer     | user daily calorie goal  |

### Networking
- We will be using a kaggle database of labeled food images in 101 categories of food to train a machine learning algorithm to recognize food from images taken by the user.
- Source for food database: "https://www.kaggle.com/kmader/food41"
- We will be saving our trained model and including it locally in the app data on the user's phone, so we don't need to use any online databases or APIs.
- We will get the information needed to map each food with its calorie count from another local dataset in csv format.
- Source for calories database: "https://www.kaggle.com/data/192954"


### Milestone3 GIF
![](caleoMilestone3.gif)
