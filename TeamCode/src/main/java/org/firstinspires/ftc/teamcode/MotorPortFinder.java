package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="wheel test frl,brl", group="Autonomous")
public class MotorPortFinder extends LinearOpMode {

    // Declare OpMode members.
    private final ElapsedTime runtime = new ElapsedTime();

    public DcMotor fl;
    public DcMotor fr;
    public DcMotor bl;
    public DcMotor br;

    @Override
    public void runOpMode() {

        fl = hardwareMap.get(DcMotor.class, "FL");
        fr = hardwareMap.get(DcMotor.class, "FR");
        bl = hardwareMap.get(DcMotor.class, "BL");
        br = hardwareMap.get(DcMotor.class, "BR");

        fl.setDirection(DcMotorSimple.Direction.REVERSE);
        fr.setDirection(DcMotorSimple.Direction.FORWARD);
        bl.setDirection(DcMotorSimple.Direction.REVERSE);
        br.setDirection(DcMotorSimple.Direction.FORWARD);

        // runs the moment robot is initialized
        waitForStart();
        runtime.reset();
//
//        fl.setPower(1);
//        sleep(2000);
//        fl.setPower(0);
//
//        fr.setPower(1);
//        sleep(2000);
//        fr.setPower(0);
//
//        bl.setPower(1);
//        sleep(2000);
//        bl.setPower(0);
//
//        br.setPower(1);
//        sleep(2000);
//        br.setPower(0);

        // runs after driver presses play
        while (opModeIsActive()) {
            double horizontal = -gamepad1.left_stick_y;
            double vertical = gamepad1.left_stick_x;
            double turn = gamepad1.right_stick_x;

            fl.setPower(Range.clip(vertical + horizontal + turn, -1, 1));
            fr.setPower(Range.clip(vertical - horizontal - turn, -1, 1));
            bl.setPower(Range.clip(vertical - horizontal + turn, -1, 1));
            br.setPower(Range.clip(vertical + horizontal - turn, -1, 1));
        }
    }
}
