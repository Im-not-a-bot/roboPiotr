/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.robot.Robot;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;



@TeleOp(name="Boxy Driver Op", group="Linear Opmode")
//@Disabled
public class BoxBotDriver extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        RobotHardware robot = new RobotHardware(hardwareMap);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            double horizontal = -gamepad1.left_stick_y;
            double vertical = gamepad1.left_stick_x;
            double turn = gamepad1.right_stick_x;
            robot.FL.setPower(Range.clip(vertical+horizontal+turn,-1,1));
            robot.FR.setPower(Range.clip(vertical-horizontal-turn,-1,1));
            robot.BL.setPower(Range.clip(vertical-horizontal+turn,-1,1));
            robot.BR.setPower(Range.clip(vertical+horizontal-turn,-1,1));


            if (gamepad1.dpad_left) robot.arm.setPower(-0.2);
            else if (gamepad1.dpad_right) robot.arm.setPower(0.2);
            else robot.arm.setPower(0);

            if (gamepad1.a) robot.carousel.setPower(1);
            else robot.carousel.setPower(0);

            if (gamepad1.b) robot.sweeper.setPower(1);
            else robot.sweeper.setPower(0);

            //if (gamepad1.x) robot.armEnd.setPower(1);
            //else if (gamepad1.y) robot.armEnd.setPower(-1);
            //else robot.armEnd.

            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Wheel Power", "FL (%.2f), FR (%.2f), BL (%.2f), BR (%.2f)", robot.FL.getPower(), robot.FR.getPower(),robot.BL.getPower(),robot.BR.getPower());
            telemetry.addData("Arm Power", robot.arm.getPower());
            telemetry.addData("Carousel Power", robot.carousel.getPower());
            telemetry.addData("Sweeper Power", robot.sweeper.getPower());
            telemetry.update();
        }
    }
}
