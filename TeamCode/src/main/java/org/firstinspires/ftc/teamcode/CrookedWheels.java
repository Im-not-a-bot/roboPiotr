package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="driving", group="Driver OP")
public class CrookedWheels extends LinearOpMode {

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

        fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

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
            move();
        }
    }

    void move(){
        double horizontal = -gamepad1.left_stick_x*.5;   // this works so dont question it
        double vertical = gamepad1.left_stick_y*.5;
        double turn = -gamepad1.right_stick_x*2/3;

        double powermultiplier = .5;

        fl.setPower(Range.clip((horizontal + vertical + turn)*powermultiplier, -1, 1));
        fr.setPower(Range.clip((horizontal - vertical - turn)*powermultiplier, -1, 1));
        bl.setPower(Range.clip((horizontal - vertical + turn)*powermultiplier, -1, 1));
        br.setPower(Range.clip((horizontal + vertical - turn)*powermultiplier, -1, 1));
    }
}
