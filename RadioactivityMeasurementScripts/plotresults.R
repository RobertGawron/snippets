

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


radioactivityGraph <- function(radiation, fileName, graphTitle){
    png(filename=fileName, height=500, width=800, bg="white")

    barNames <- sprintf("%d", c(1:length(radiation)))

    df.bar <- barplot(radiation, names.arg=barNames, ylim=c(0,200), main=graphTitle, ylab="counts / minute", xlab="sample id", xpd=TRUE)

    lines(x=df.bar, y=rep(mean(radiation), length(radiation)))
    points(x=df.bar, y=rep(mean(radiation), length(radiation)), pch=19)

    grid(nx=NA, ny=NULL)

    legend("bottom", "mean", pch=19)

    dev.off()

    return(NULL)
}


#--
radioactivityGraph(samples1, "EnviromentalRadiactivity.png", "Enviromental radioactivity")
radioactivityGraph(samples2, "PotassiumGlassShield.png", "Potassium behind a glass shield")
radioactivityGraph(samples3, "Potassium.png", "Potassium")
radioactivityGraph(samples4, "Ashes.png", "Ashes (remainings after potassium extraction)")






#---


png(filename="RadiactivitySummary.png", height=500, width=800, bg="white")

graphData <- c(m1, m2, m3, m4)
barplot(rep(NA,length(graphData)),ylim=c(0, 1.25*max(graphData)),axes=FALSE)
grid(nx=NA, ny=NULL)


barplot(graphData, main="Comparation of sample radioactivity", xlab="", add=T,
    ylab="mean (counts / minute)",  ylim=c(0, 1.25*max(graphData)), xpd=TRUE,
	names.arg=c("envirmomental", "potassium shielded by glass", "potassium", "ashes after extraction"))

dev.off()


warnings()
