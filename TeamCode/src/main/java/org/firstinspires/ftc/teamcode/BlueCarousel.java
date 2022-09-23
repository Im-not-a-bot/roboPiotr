package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;


@Autonomous(name = "Blue Carousel", group = "Autonomous")
public class BlueCarousel extends LinearOpMode {
    private final ElapsedTime runtime = new ElapsedTime();

    DcMotor fl = hardwareMap.get(DcMotor.class, "FL");
    DcMotor fr = hardwareMap.get(DcMotor.class, "FR");
    DcMotor bl = hardwareMap.get(DcMotor.class, "BL");
    DcMotor br = hardwareMap.get(DcMotor.class, "BR");



    @Override
    public void runOpMode() {
        // runs the moment robot is initialized

        waitForStart();

        fl.setPower(1);
        sleep(2000);
        fl.setPower(0);

        fr.setPower(1);
        sleep(2000);
        fr.setPower(0);

        bl.setPower(1);
        sleep(2000);
        bl.setPower(0);

        br.setPower(1);
        sleep(2000);
        br.setPower(0);

        //yay ig

        //while (opModeIsActive()) {
        //}
    }
}