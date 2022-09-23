package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Crooked OP", group="Driver Op")
public class Crooked_Driver_OP extends LinearOpMode {


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
