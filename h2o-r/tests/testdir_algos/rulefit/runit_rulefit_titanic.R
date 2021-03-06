setwd(normalizePath(dirname(R.utils::commandArgs(asValues=TRUE)$"f")))
source("../../../scripts/h2o-r-test-setup.R")



# Test RuleFit on titanic.csv
test.rulefit.titanic <- function() {
    
    Log.info("Importing titanic.csv...\n")
    titanic = h2o.uploadFile(locate("smalldata/gbm_test/titanic.csv"))

    response = "survived"
    predictors <- c("age", "sibsp", "parch", "fare", "sex", "pclass")

    titanic[,response] <- as.factor(titanic[,response])
    titanic[,"pclass"] <- as.factor(titanic[,"pclass"])

    rf_h2o = h2o.rulefit(y=response, x=predictors, training_frame = titanic, max_rule_length=10, max_num_rules=100, seed=1234, model_type="rules")

    print(rf_h2o@model$rule_importance)
  
}

doTest("RuleFit Test: Titanic Data", test.rulefit.titanic)
