package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Motor Port Finder", group="Autonomous")
public class MotorPortFinder extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();


    CrookedBot robot;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        robot = new CrookedBot(hardwareMap);

        // runs the moment robot is initialized
        waitForStart();
        runtime.reset();

        // runs after driver presses play
        while (opModeIsActive()) {

            robot.FR.setPower(0);
            robot.FL.setPower(0);
            robot.BR.setPower(0);
            robot.BL.setPower(0);
            if(gamepad1.dpad_up){
                robot.FR.setPower(1);
                telemetry.addData("Motor Type", "Front Right");
            }else if(gamepad1.dpad_left){
                robot.FL.setPower(1);
                telemetry.addData("Motor Type", "Front Left");
            }else if(gamepad1.dpad_down){
                robot.BL.setPower(1);
                telemetry.addData("Motor Type", "Back Left");
            }else if(gamepad1.dpad_right){
                robot.BR.setPower(1);
                telemetry.addData("Motor Type", "Back Right");
            }

            telemetry.update();
        }
    }
}
