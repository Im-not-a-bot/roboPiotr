package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Crooked OP", group="Driver Op")
public class Crooked_Driver_OP extends LinearOpMode {

    DcMotor joe0 = hardwareMap.get(DcMotor.class, "l1");;
    DcMotor joe1 = hardwareMap.get(DcMotor.class, "l2");;
    DcMotor joe2 = hardwareMap.get(DcMotor.class, "l3");;
    DcMotor joe3 = hardwareMap.get(DcMotor.class, "lbozo");;



    @Override
    public void runOpMode() {
        // runs the moment robot is initialized

        waitForStart();

        while (opModeIsActive()) {
            // movement n' shit
            joe1.setPower(1);
        }
    }
}
