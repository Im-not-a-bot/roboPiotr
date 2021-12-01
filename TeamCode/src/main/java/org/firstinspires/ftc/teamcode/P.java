package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;

import android.graphics.Point;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.robot.Robot;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;
@TeleOp(name="peat test", group="Linear Opmode")
public class P extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();


    public DcMotor FR;
    public DcMotor FL;
    public DcMotor BR;
    public DcMotor BL;

    public DcMotor carousel;
    public DcMotor arm;
    public Servo armEnd;
    public DcMotor sweeper;

    public long pt;
    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        //on ctrl hub
        FR  = hardwareMap.get(DcMotor.class, "FR");
        FL = hardwareMap.get(DcMotor.class, "FL");
        BR = hardwareMap.get(DcMotor.class, "BR");
        BL = hardwareMap.get(DcMotor.class, "BL");
        armEnd = hardwareMap.get(Servo.class,"servo");

        //on expansion hub
        arm = hardwareMap.get(DcMotor.class, "arm");
        sweeper = hardwareMap.get(DcMotor.class, "sweeper");
        carousel = hardwareMap.get(DcMotor.class, "carousel");

        // runs the moment  is initialized
        waitForStart();
        runtime.reset();

        armEnd.resetDeviceConfigurationForOpMode();
        // runs after driver presses play
        while (opModeIsActive()) {
            motors();

            if (gamepad1.dpad_right){arm.setTargetPosition(arm.getTargetPosition()+1);}
            if (gamepad1.dpad_left){arm.setTargetPosition(arm.getTargetPosition()-1);}
            if (gamepad1.dpad_down){armEnd.setPosition(-180);}
            if (gamepad1.dpad_up){armEnd.setPosition(0);}

            if(gamepad1.a){sweeper.setPower(-1);}else{sweeper.setPower(0);}
            if(gamepad1.x){carousel.setPower(-1);}else{carousel.setPower(0);}
            if(gamepad1.a){carousel.setPower(1);}else{carousel.setPower(0);}

            telemetry.addLine(
                    "UpS (ms)"+  1000/((double)(System.nanoTime()-pt)/1000000)+"\n"+
                              "motor position:" + "\n"+
                            "FR: "+FR.getCurrentPosition()+"\n" +
                            "FL: "+FL.getCurrentPosition()+"\n" +
                            "BR: "+BR.getCurrentPosition()+"\n" +
                            "BL: "+BL.getCurrentPosition()+"\n" +
                            "arm "+arm.getCurrentPosition()+"\n"+
                            "servo "+armEnd.getPosition()+"\n"+
                            "sweeper "+ sweeper.getCurrentPosition()+"\n"
            );
            pt=System.nanoTime();
            telemetry.update();
        }
    }

    void motors() {
        //complex trial-and-error stuff
        FR.setPower(Range.clip(-gamepad1.left_stick_y-gamepad1.left_stick_x-.75*gamepad1.right_stick_x,-1,1));
        FL.setPower(Range.clip(-gamepad1.left_stick_y+gamepad1.left_stick_x+.75*gamepad1.right_stick_x,-1,1));
        BR.setPower(Range.clip(-gamepad1.left_stick_y+gamepad1.left_stick_x-.75*gamepad1.right_stick_x,-1,1));
        BL.setPower(Range.clip(-gamepad1.left_stick_y-gamepad1.left_stick_x+.75*gamepad1.right_stick_x,-1,1));
    }
}
