

sprintf("Environmental radiation level")
samples1 <- c(57, 48, 73, 62, 75, 58, 63, 55, 61, 70)
m1 <- mean(samples1)
sprintf("   Mean: %f", m1)
sprintf("   Standard deviation: %f", sd(samples1))
sprintf("")


sprintf("Potassium in a Petri dish on the sensor")
samples2 <- c(69, 60, 61, 50, 69, 61, 59, 57, 66, 67)
m2 <- mean(samples2)
sprintf("   Mean: %f", m2)
sprintf("   Standard deviation: %f", sd(samples2))
sprintf("")


sprintf("Potassium in a Petri dish under the sensor")
samples3 <- c(182, 160, 149, 166, 149, 166, 162, 183, 183, 166)
m3 <- mean(samples3)
sprintf("   Mean: %f", m3)
sprintf("   Standard deviation: %f", sd(samples3))
sprintf("")

sprintf("Ashes on the sensor")
samples4 <- c(99, 123, 89, 96, 103, 96, 88, 91, 86, 98)
m4 <- mean(samples4)
sprintf("   Mean: %f", m4)
sprintf("   Standard deviation: %f", sd(samples4))
sprintf("")



#--

png(filename="EnvRadiactivityGraph.png", height=500, width=800, bg="white")

graphData <- samples1

plot(x=c(1:length(graphData)), y=graphData, main="Environmental Radioactivity Level", xlab="samples", type="p",xaxs = "i",yaxs="i",
    ylab="counts / minute",  ylim=c(0, 1.1*max(graphData)), xpd=TRUE)
grid(col="black")

#---

png(filename="PotassiumObstacleRadiactivityGraph.png", height=500, width=800, bg="white")

graphData <- samples2

plot(x=c(1:length(graphData)), y=graphData, main="Potassium (+glass obstacle) Radioactivity Level", xlab="samples", type="p",xaxs = "i",yaxs="i",
    ylab="counts / minute",  ylim=c(0, 1.1*max(graphData)), xpd=TRUE)
grid(col="black")

#---

png(filename="PotassiumRadiactivityGraph.png", height=500, width=800, bg="white")

graphData <- samples3

plot(x=c(1:length(graphData)), y=graphData, main="Potassium Radioactivity Level", xlab="samples", type="p",xaxs = "i",yaxs="i",
    ylab="counts / minute",  ylim=c(0, 1.1*max(graphData)), xpd=TRUE)
grid(col="black")

#---

png(filename="AshRadiactivityGraph.png", height=500, width=800, bg="white")

graphData <- samples4

plot(x=c(1:length(graphData)), y=graphData, main="Cigarette Ash Radioactivity Level", xlab="samples", type="p",xaxs = "i",yaxs="i",
    ylab="counts / minute",  ylim=c(0, 1.1*max(graphData)), xpd=TRUE)
grid(col="black")


png(filename="RadiactivityGraph.png", height=500, width=800, bg="white")

graphData <- c(m1, m2, m3, m4)
barplot(rep(NA,length(graphData)),ylim=c(0, 1.25*max(graphData)),axes=FALSE)
grid(col="black")
barplot(graphData, main="Comparation Radioactivity Level", xlab="", add=T,
    ylab="counts / minute",  ylim=c(0, 1.25*max(graphData)), xpd=TRUE,
	names.arg=c("no sample", "potassium (obstacle)", "potassium", "ash remaining"))

warnings()