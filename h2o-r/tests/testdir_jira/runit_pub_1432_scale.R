setwd(normalizePath(dirname(R.utils::commandArgs(asValues=TRUE)$"f")))
source("../../scripts/h2o-r-test-setup.R")



test <- function() {
  foo <- as.h2o(iris)
  print(foo)
  frame <- scale(foo[,1:4],center=c(4,3,2,1), scale=c(1,1,1,1))
  print(frame)
  frame <- scale(foo[,1:4],center=c(4,3,2,1), scale=TRUE)
  print(frame)
  frame <- scale(foo[,1:4],center=T, scale=c(1,1,1,1))
  print(frame)
  frame <- scale(foo[,1:4],center=c(4,3,2,1), scale=FALSE)
  print(frame)
  frame <- scale(foo[,1:4],center=F, scale=c(1,1,1,1))
  print(frame)
  
}

doTest("testing scale", test)
