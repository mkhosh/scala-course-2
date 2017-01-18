import quickcheck.{Circuits, Parameters}
object sim extends Circuits with Parameters

import sim._

val in1, in2, sum, carry = new Wire

halfAdder(in1, in2, sum, carry)
probe("sum", sum)
probe("carry", carry)

sim.lengthOfAgenda
in1.setSignal(true)

sim.lengthOfAgenda
run()

in2.setSignal(true)
run()
