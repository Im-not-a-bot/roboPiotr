package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Crooked Wheels", group="Autonomous")
public class CrookedWheels extends LinearOpMode {

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
            double vertical = -gamepad1.left_stick_y;
            double horizontal = gamepad1.left_stick_x;
            double turn = gamepad1.right_stick_x;


            robot.FL.setPower(Range.clip(vertical + horizontal + turn, -1, 1));
            robot.FR.setPower(Range.clip(vertical - horizontal - turn, -1, 1));
            robot.BL.setPower(Range.clip(vertical - horizontal + turn, -1, 1));
            robot.BR.setPower(Range.clip(vertical + horizontal - turn, -1, 1));

            if (gamepad1.dpad_up){
                robot.chain.setPower(1);
            }else if (gamepad1.dpad_down){
                robot.chain.setPower(-1);
            }else{
                robot.chain.setPower(0);
            }

            if (gamepad1.dpad_right){
                robot.carL.setPower(1);
                robot.carR.setPower(1);
            }else if (gamepad1.dpad_left){
                robot.carL.setPower(-1);
                robot.carR.setPower(-1);
            }else{
                robot.carL.setPower(0);
                robot.carR.setPower(0);
            }

            telemetry.addData("Motors", "FL (%.2f), FR (%.2f), BL (%.2f), BR (%.2f)", robot.FL.getPower(), robot.FR.getPower(), robot.BL.getPower(), robot.BR.getPower());
            telemetry.addData("Encoders", "FL (%d), FR (%d), BL (%d), BR (%d)", robot.FL.getCurrentPosition(), robot.FR.getCurrentPosition(), robot.BL.getCurrentPosition(), robot.BR.getCurrentPosition());

            telemetry.update();
        }
    }
}
