package quickcheck

/**
  * Created by mkhoshneshin on 1/10/17.
  *
  * Digital circuit has wires and functional components (gates)
  * Gates:
  *   - Inverter
  *   - AND Gate
  *   - OR Gate
  * The components have delay
  *
  * Signal is a boolean
  */
class DiscreteSimul {
  type Action = () => Unit
}
