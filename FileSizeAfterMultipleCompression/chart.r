args<-commandArgs(TRUE)
sizes <- scan(args[1])
image_name <- paste( "./chart", args[1], ".png")

png(filename=image_name, width=500, height=700, bg="white")

barplot(sizes, main=args[2], xlab="iterations", 
    ylab="size of zip archive in bytes", xpd=F, col="grey",
    ylim=(c(min(sizes), max(sizes))))

axis(2, at=c(min(sizes), max(sizes)))
