(define (problem taquin-3-3)
(:domain taquin)
(:objects a b c d e f g h - number
          cella cellb cellc
          celld celle cellf
          cellg cellh cellempty - cell)
;; Problem demanding a one small cycle on cells e, f, h and empty:
(:init 
  (on a cella)
  (on b cellb)
  (on c cellc)
  (on d celld)
  (on f celle)
  (empty cellf)
  (on g cellg)
  (on e cellh)
  (on h cellempty)

  ;; Adjacency relationships
  (adjacent cella cellb) (adjacent cellb cella)
  (adjacent cella celld) (adjacent celld cella)
  (adjacent cellb cellc) (adjacent cellc cellb)
  (adjacent cellb celle) (adjacent celle cellb)
  (adjacent cellc cellf) (adjacent cellf cellc)
  (adjacent celld celle) (adjacent celle celld)
  (adjacent celld cellg) (adjacent cellg celld)
  (adjacent celle cellf) (adjacent cellf celle)
  (adjacent celle cellh) (adjacent cellh celle)
  (adjacent cellf cellempty) (adjacent cellempty cellf)
  (adjacent cellg cellh) (adjacent cellh cellg)
  (adjacent cellh cellempty) (adjacent cellempty cellh)
)
;; Letter to letter correspondance
(:goal 
  (and 
    (on a cella)
    (on b cellb)
    (on c cellc)
    (on d celld)
    (on e celle)
    (on f cellf)
    (on g cellg)
    (on h cellh)
  )
)
)
