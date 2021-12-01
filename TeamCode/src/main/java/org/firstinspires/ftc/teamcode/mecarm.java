package org.firstinspires.ftc.teamcode;

import android.graphics.Point;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;
@TeleOp(name="mecarm", group="Linear Opmode")
public class mecarm extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();

    // REMEMBER!! the robot is SIDEWAYS. The front is what would normally be right
    // naming is based on new orientation!

    public DcMotor FR;
    public DcMotor FL;
    public DcMotor BR;
    public DcMotor BL;

    public DcMotor arm;
    //
//    public DcMotor R;
//    public DcMotor L;
//
//    public Servo RS;
//    public Servo LS;
//
//    public Point p;
//
//    //for arm calcs
//    public long pt=0;
//    public double h=0;
//
//    public double ang1;
//    public double ang2;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        FR  = hardwareMap.get(DcMotor.class, "FR");
        FL = hardwareMap.get(DcMotor.class, "FL");
        BR = hardwareMap.get(DcMotor.class, "BR");
        BL = hardwareMap.get(DcMotor.class, "BL");
        arm = hardwareMap.get(DcMotor.class, "arm");

        //
//        R = hardwareMap.get(DcMotor.class, "R");
//        L = hardwareMap.get(DcMotor.class, "L");
//
//        RS = hardwareMap.get(Servo.class, "RS");
//        LS = hardwareMap.get(Servo.class, "LS");
//        //servo.setPosition(0);
//
//        p=new Point(0,0);

        // runs the moment robot is initialized
        waitForStart();
        runtime.reset();

        //resets encoders
        FR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        // makes motors "stiff" when unpowered
        FR.setZeroPowerBehavior(BRAKE);
        FL.setZeroPowerBehavior(BRAKE);
        BR.setZeroPowerBehavior(BRAKE);
        BL.setZeroPowerBehavior(BRAKE);

        arm.setZeroPowerBehavior(BRAKE);

        //makes accuracy remotely possible
        FR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //makes running to angle possible

        arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        arm.setTargetPosition(arm.getCurrentPosition());
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);


        // runs after driver presses play
        while (opModeIsActive()) {
                motors();
                if(gamepad1.x){arm.setTargetPosition(arm.getCurrentPosition());arm.setPower(.01);}else{arm.setPower(1);}

            if (gamepad1.dpad_right) {arm.setTargetPosition(arm.getTargetPosition()+1);}
            if (gamepad1.dpad_left) {arm.setTargetPosition(arm.getTargetPosition()-1);}

            telemetry.addLine(//"UT (ms)"+  ((double)(System.nanoTime()-pt)/1000000)+"\n"+
                                 "motor position:" + "\n"+
                                 "FR: "+FR.getCurrentPosition()+"\n" +
                                 "FL: "+FL.getCurrentPosition()+"\n" +
                                 "BR: "+BR.getCurrentPosition()+"\n" +
                                 "BL: "+BL.getCurrentPosition()+"\n" +
                                 "\n"
//                                 "arm position:" +"\n"+
//                                 "Target point "+p+"\n"+
//                                 "R "+R.getCurrentPosition()+"\n"+
//                                 "L "+L.getCurrentPosition()+"\n"+
//                                 "RS "+RS.getPosition()+"\n"+
//                                 //"LS "+RS.getPosition()
            );
            //pt=System.nanoTime();
            telemetry.update();
        }
    }

    void motors(){

        FR.setDirection(DcMotorSimple.Direction.REVERSE);
        FL.setDirection(DcMotorSimple.Direction.FORWARD);
        BR.setDirection(DcMotorSimple.Direction.FORWARD);
        BL.setDirection(DcMotorSimple.Direction.REVERSE);

        //complex trial-and-error stuff
        FR.setPower(Range.clip(-gamepad1.left_stick_y-gamepad1.left_stick_x-.75*gamepad1.right_stick_x,-1,1));
        FL.setPower(Range.clip(-gamepad1.left_stick_y+gamepad1.left_stick_x+.75*gamepad1.right_stick_x,-1,1));
        BR.setPower(Range.clip(-gamepad1.left_stick_y+gamepad1.left_stick_x-.75*gamepad1.right_stick_x,-1,1));
        BL.setPower(Range.clip(-gamepad1.left_stick_y-gamepad1.left_stick_x+.75*gamepad1.right_stick_x,-1,1));

    }
}
