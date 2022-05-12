package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Crooked OP", group="Driver Op")
public class Crooked_Driver_OP extends LinearOpMode {

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

            if (gamepad1.x) {
                // carR
                if (gamepad1.right_trigger > 0.5) robot.carR.setPower(1);
                else if (gamepad1.right_bumper) robot.carR.setPower(-1);
                else robot.carR.setPower(0);

                // carL
                if (gamepad1.left_trigger > 0.5) robot.carL.setPower(1);
                else if (gamepad1.left_bumper) robot.carL.setPower(-1);
                else robot.carL.setPower(0);
            } else {
                // carL
                if (gamepad1.left_trigger > 0.5) {
                    robot.carL.setPower(1);
                    robot.carR.setPower(1);
                }
                else if (gamepad1.right_trigger > 0.5) {
                    robot.carL.setPower(-1);
                    robot.carR.setPower(-1);
                }
                else {
                    robot.carL.setPower(0);
                    robot.carR.setPower(0);
                }
            }

            /*
            // carR
            if (gamepad1.right_trigger > 0.5) robot.carR.setPower(1);
            else if (gamepad1.right_bumper) robot.carR.setPower(-1);
            else robot.carR.setPower(0);
             */

            // chain
            if (gamepad1.dpad_up) robot.chain.setPower(0.4);
            else if (gamepad1.dpad_down) robot.chain.setPower(-0.4);
            else robot.chain.setPower(0);



            robot.FL.setPower(Range.clip(vertical + horizontal + turn, -1, 1));
            robot.FR.setPower(Range.clip(vertical - horizontal - turn, -1, 1));
            robot.BL.setPower(Range.clip(vertical - horizontal + turn, -1, 1));
            robot.BR.setPower(Range.clip(vertical + horizontal - turn, -1, 1));

            telemetry.addData("Motors", "FL (%.2f), FR (%.2f), BL (%.2f), BR (%.2f)", robot.FL.getPower(), robot.FR.getPower(), robot.BL.getPower(), robot.BR.getPower());
            telemetry.addData("Encoders", "FL (%d), FR (%d), BL (%d), BR (%d)", robot.FL.getCurrentPosition(), robot.FR.getCurrentPosition(), robot.BL.getCurrentPosition(), robot.BR.getCurrentPosition());
            telemetry.update();
        }
    }
}
